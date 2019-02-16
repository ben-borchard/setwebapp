package com.bborchard.setapp.web.config;

import com.bborchard.setapp.GameStore;
import com.bborchard.setapp.model.State;
import com.bborchard.setapp.web.SetAppSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

public class Listeners {

  @Autowired
  private SetAppSender messageSender;

  public ApplicationListener<SessionDisconnectEvent> onDisconnect() {

    return new ApplicationListener<SessionDisconnectEvent>() {
      @Override
      public void onApplicationEvent(SessionDisconnectEvent sessionDisconnectEvent) {
        // get information
        String sessionId = sessionDisconnectEvent.getSessionId();
        String gameName = GameStore.getUsers().get(sessionId);
        State state = GameStore.getGames().get(gameName).getState();

        // update store
        state.getUsersMap().remove(sessionId);
        GameStore.getUsers().remove(sessionId);
        if (state.getUsers().isEmpty()) {
          GameStore.getGames().remove(gameName); // no users means no game
        }

        // send update
        messageSender.send(gameName, state);
      }
    };
  }

}
