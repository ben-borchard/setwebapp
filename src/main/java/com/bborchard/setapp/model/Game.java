package com.bborchard.setapp.model;

import com.bborchard.setapp.SetTimer;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class Game {
  private String name;
  private State state;

  @JsonIgnore
  private SetTimer timer;

  public Game(String name) {
    this.name = name;
    this.state = new State();
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public State getState() {
    return state;
  }

  public void setState(State state) {
    this.state = state;
  }

  public void setTimer(SetTimer timer) {
    this.timer = timer;
  }

  public void cancelTimerAndWait() {
    if (timer != null) { // null check mostly to facilitate hacks for testing
      timer.cancelAndWait();
    }
  }

}
