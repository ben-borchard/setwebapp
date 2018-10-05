package com.bborchard.setapp;

import com.bborchard.setapp.Model.Game;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class GameController {

  @RequestMapping("/")
  public List<String> index() {
    return GameStore.getGames().stream().map(Game::getName).collect(Collectors.toList());
  }

  @RequestMapping("/create")
  public void create() {
    // create the socket endpoint and game
    join("gameId");
  }

  @RequestMapping("/join/{id}")
  public void join(@PathVariable String id) {
  }

}
