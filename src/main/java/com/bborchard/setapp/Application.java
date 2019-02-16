package com.bborchard.setapp;

import com.bborchard.setapp.web.config.Listeners;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class Application {

  public static void main(String[] args) {
    ConfigurableApplicationContext context = SpringApplication.run(Application.class, args);

    // add the session disconnect listener
    Listeners listeners = context.getBean(Listeners.class);
    context.addApplicationListener(listeners.onDisconnect());
  }
  
}