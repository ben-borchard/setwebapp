package com.bborchard.setapp;

import com.bborchard.setapp.model.Game;
import com.bborchard.setapp.model.State;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class GameStore {

  private static ConcurrentMap<String, Game> games = new ConcurrentHashMap<>();

  // this is needed for the SessionDisconnectEvent
  private static ConcurrentMap<String, String> usersLink = new ConcurrentHashMap<>();

  public static ConcurrentMap<String, Game> getGames() {
    return games;
  }

  public static ConcurrentMap<String, String> getUsers() {
    return usersLink;
  }

  public static void add(String name) {
    if (games.putIfAbsent(name, new Game(name)) != null) {
      throw new IllegalArgumentException(String.format("Game with name %s already exists", name));
    }
  }

  public static State getState(String gameName) {
    return games.get(gameName).getState();
  }

}
