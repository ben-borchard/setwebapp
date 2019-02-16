package com.bborchard.setapp.web.controller;

import com.bborchard.setapp.*;
import com.bborchard.setapp.model.Card;
import com.bborchard.setapp.model.Game;
import com.bborchard.setapp.model.State;
import com.bborchard.setapp.model.User;
import com.bborchard.setapp.web.config.SetAppConfig;
import com.bborchard.setapp.web.SetAppSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

@RestController
public class GameController {

  Logger log = LoggerFactory.getLogger(GameController.class);

  @Autowired
  private SetAppSender messageSender;

  @GetMapping("/getGames")
  public Set<String> getGames() {
    return GameStore.getGames().keySet();
  }

  @GetMapping("/randomName")
  public String getRandomName() {
    return NameGenerator.randomName();
  }

  @PostMapping("/create")
  public void create(@RequestParam("name") String name) throws Exception {
    name = name.trim();
    // don't add a game without a proper name
    if (name.length() != 0) {
      GameStore.add(name);
    }
  }

  @MessageMapping("/{gameName}/addThree")
  @SendTo(SetAppConfig.BROKER_PREFIX+"/{gameName}")
  public State addThree(@Header("simpSessionId") String sessionId, @DestinationVariable String gameName) {
    State state = GameStore.getState(gameName);
    // client should block this, but be safe anyway
    if (!state.getDeckStack().isEmpty()) {
      state.voteForThreeMore(sessionId);
    }
    return state;
  }

  @MessageMapping("/{gameName}/foundSet")
  public void foundSet(@Header("simpSessionId") String sessionId, @DestinationVariable String gameName) {
    Game game = GameStore.getGames().get(gameName);
    if (game.getState().setFound(sessionId)) {
      // this user had the fastest finger
      log.info("Game {} - user {} has found a set", gameName, sessionId);
      SetTimer timer = new SetTimer(game, sessionId, messageSender);
      messageSender.send(gameName, game.getState());
      game.setTimer(timer);
      // set the countdown in motion
      timer.countdown();
    }
  }

  @MessageMapping("/{gameName}/submitSet")
  public void submitSet(Integer[] set, @Header("simpSessionId") String sessionId, @DestinationVariable String gameName) {
    Game game = GameStore.getGames().get(gameName);
    // make sure we actually made it before time expired
    game.cancelTimerAndWait();
    if (game.getState().getUserFindingSet() != null) {
      // we made it!
      List<Card> board = game.getState().getBoard(); // ease of access

      // is it actually a set though?
      if (SetUtils.isSet(board.get(set[0]), board.get(set[1]), board.get(set[2])))  {
        // yes - reward the user
        game.getState().getUsersMap().get(sessionId).takeSet();

        // reflect set in the board
        if (board.size() > 12 || game.getState().getDeckStack().isEmpty())  {
          // just remove cards if some were added or no cards are left
          Iterator<Card> it = board.iterator();
          for (int i = 0; it.hasNext(); i++) {
            it.next();
            if (i == set[0] || i == set[1] || i == set[2]) {
              it.remove();
            }
          }
        } else {
          // replace set with new cards from deck
          for (int idx : set) {
            board.set(idx, game.getState().getDeckStack().pop());
          }
          // reset votes for three more as new cards are going on the board
          game.getState().resetVotesForThreeMore();
        }

        // make sure game can still continue
        if (game.getState().getDeckStack().isEmpty() && !SetUtils.hasSet(game.getState().getBoard())) {
          // game is over - determine the winner
          User winner = null;
          for (User user : game.getState().getUsersMap().values()) {
            if (winner == null || user.getSets() > winner.getSets()) {
              winner = user;
            }
          }
          game.getState().setWinner(winner);
        }
      } else {
        // no - penalize user
        game.getState().getUsersMap().get(sessionId).penalize();
      }

      // reset and send update only if we submitted in time (otherwise SetTimer will)
      game.getState().resetFromFoundSet();
      messageSender.send(gameName, game.getState());
    }
  }

  /**
   * For cheating/testing purposes
   */
  @MessageMapping("/{gameName}/findSet")
  public void findSet(@Header("simpSessionId") String sessionId, @DestinationVariable String gameName) {
    Game game = GameStore.getGames().get(gameName);

    Integer[] set = SetUtils.findSet(game.getState().getBoard());
    while (set == null && !game.getState().getBoard().isEmpty()) {
      // add three more forcibly if need be
      game.getState().addThreeMore();
      set = SetUtils.findSet(game.getState().getBoard());
    }

    if (set != null) {
      // we found  a set - say so
      foundSet(sessionId, gameName);

      // wait a hair for found message to make it to clients
      try {
        Thread.sleep(100);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }

      // submit the set
      submitSet(set, sessionId, gameName);
    }
  }

  @MessageMapping("/{gameName}/join")
  @SendTo(SetAppConfig.BROKER_PREFIX+"/{gameName}")
  public State join(String userName, @Header("simpSessionId") String sessionId, @DestinationVariable String gameName) {

    // update the store
    State gameState = GameStore.getState(gameName);
    gameState.getUsersMap().put(sessionId, new User(sessionId, userName));
    GameStore.getUsers().put(sessionId, gameName);

    return gameState;
  }

}
