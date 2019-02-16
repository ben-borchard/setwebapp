package com.bborchard.setapp.model;

import com.bborchard.setapp.SetUtils;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;


public class State {

  public static final int INITIAL_BOARD_SIZE = 12;

  private Stack<Card> deck;
  private List<Card> board;
  private Map<String, User> users;
  private int addThreeVotes;
  private AtomicInteger timer;
  private String userFindingSet;
  private User winner;

  public State() {

    // initialize users
    users = new HashMap<>();

    // create the deck
    deck = SetUtils.shuffleDeck(SetUtils.createDeck());

    // initialize the board
    board = new ArrayList<>(INITIAL_BOARD_SIZE);
    for (int i = 0; i < INITIAL_BOARD_SIZE; i++) {
      board.add(deck.pop());
    }

    resetFromFoundSet();
  }

  @JsonProperty("userFindingSet")
  public User getUserFindingSet() {
    if (userFindingSet != null && this.users.containsKey(userFindingSet)) {
      return this.users.get(userFindingSet);
    }
    return null;
  }

  public synchronized void voteForThreeMore(String userSession) {
    User user = users.get(userSession);
    // user can only vote once
    if (!user.isWantsThreeMore()) {
      user.setWantsThreeMore(true);
      // actually add three more if all users want more
      if (++addThreeVotes == users.size()) {
        addThreeMore();
      }
    }
  }

  public void addThreeMore() {
    for (int i = 0; i < 3; i++) {
      board.add(deck.pop());
    }
    resetVotesForThreeMore();
  }

  /**
   * Denote that a user has found a set
   * @return true if the user was the first to find the set - otherwise false
   */
  public synchronized boolean setFound(String user) {
    if (userFindingSet == null) {
      userFindingSet = user;
      return true;
    }
    return false;
  }

  public int tickDown() {
    return timer.decrementAndGet();
  }

  public void resetFromFoundSet() {
    timer = new AtomicInteger(5);
    userFindingSet = null;
  }

  public void resetVotesForThreeMore() {
    addThreeVotes = 0;
    for (User userToReset : users.values()) {
      userToReset.setWantsThreeMore(false);
    }
  }

  public int getDeck() {
    return deck.size(); // the only thing the client needs is the number left
  }

  @JsonIgnore
  public Map<String, User> getUsersMap() {
    return users;
  }

  @JsonIgnore
  public Stack<Card> getDeckStack() {
    return deck;
  }

  public List<Card> getBoard() {
    return board;
  }

  public void setBoard(List<Card> board) {
    this.board = board;
  }

  public Collection<User> getUsers() {
    return users.values();
  }

  public int getAddThreeVotes() {
    return addThreeVotes;
  }

  public void setAddThreeVotes(int addThreeVotes) {
    this.addThreeVotes = addThreeVotes;
  }

  public int getTimer() {
    return timer.get();
  }

  public void setTimer(int timer) {
    this.timer.set(timer);
  }

  public User getWinner() {
    return winner;
  }

  public void setWinner(User winner) {
    this.winner = winner;
  }
}
