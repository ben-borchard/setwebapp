package com.bborchard.setapp;

import com.bborchard.setapp.model.Game;
import com.bborchard.setapp.web.SetAppSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Timer;
import java.util.TimerTask;

public class SetTimer {

  static Logger log = LoggerFactory.getLogger(SetTimer.class);

  private Game game;
  private String user;
  private SetAppSender messageSender;
  private volatile boolean inTask;

  private Timer timer;

  public SetTimer(Game game, String user, SetAppSender messageSender) {
    this.game = game;
    this.user = user;
    this.messageSender = messageSender;
    this.timer = new Timer();
  }

  public void countdown() {
    timer.scheduleAtFixedRate(new Countdown(), 1000, 1000);
  }

  public void cancelAndWait() {
    timer.cancel();
    while (inTask) {
      try {
        Thread.sleep(1);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }

  private class Countdown extends TimerTask {

    @Override
    public void run() {
      inTask = true;
      try {
        int count = game.getState().tickDown();
        log.info("Game {} - count down to {}", game.getName(), count);
        if (count == 0) {
          log.info("Game {} - time is up", game.getName(), count);
          // time is up
          game.getState().getUsersMap().get(user).penalize();
          game.getState().resetFromFoundSet();
          messageSender.send(game.getName(), game.getState());
          timer.cancel();
        } else {
          messageSender.send(game.getName(), game.getState());
        }
      } finally {
        inTask = false;
      }
    }
  }
}
