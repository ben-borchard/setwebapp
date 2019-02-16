package com.bborchard.setapp.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;

/**
 * Just a simple sending wrapper to contain application specific sending logic
 */
public class SetAppSender {

  @Autowired
  SimpMessagingTemplate internalSender;

  private String brokerPrefix;

  public SetAppSender(String brokerPrefix) {
    this.brokerPrefix = brokerPrefix;
  }

  public void send(String game, Object payload) {
    internalSender.convertAndSend(brokerPrefix + "/" + game, payload);
  }

}
