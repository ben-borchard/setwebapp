package com.bborchard.setapp.web.config;

import com.bborchard.setapp.web.SetAppSender;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class SetAppConfig implements WebSocketMessageBrokerConfigurer {

  public static final String BROKER_PREFIX = "/state";

  @Override
  public void registerStompEndpoints(StompEndpointRegistry registry) {
    registry.addEndpoint("/game/connect").withSockJS();
  }

  @Override
  public void configureMessageBroker(MessageBrokerRegistry config) {
    config.enableSimpleBroker(BROKER_PREFIX);
    config.setApplicationDestinationPrefixes("/update");
  }

  @Bean
  public Listeners getListeners() {
      return new Listeners();
  }

  @Bean
  public SetAppSender getSetAppSender() {
    return new SetAppSender(BROKER_PREFIX);
  }

}
