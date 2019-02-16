package com.bborchard.setapp.web.listener;

import org.springframework.context.ApplicationListener;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;

public class SessionSubscribeEventListener implements ApplicationListener<SessionSubscribeEvent> {

  @Override
  public void onApplicationEvent(SessionSubscribeEvent sessionSubscribeEvent) {
    System.out.println(sessionSubscribeEvent.getMessage().getHeaders().get("destination"));

  }
}
