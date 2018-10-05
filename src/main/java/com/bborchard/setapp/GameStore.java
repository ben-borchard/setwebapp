package com.bborchard.setapp;

import com.bborchard.setapp.Model.Game;

import java.util.List;

public class GameStore {

  private static List<Game> games;

  public static List<Game> getGames() {
    return games;
  }

}
