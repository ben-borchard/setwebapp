package com.bborchard.setapp.model;

public class User {
  private String name;
  private int sets;
  private boolean wantsThreeMore = false;
  private String sessionId;

  public User(String sessionId, String name) {
    this.sessionId = sessionId;
    this.name = name;
  }

  public void foundSet() {
    sets++;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void penalize() {
    sets--;
  }

  public void takeSet() {
    sets++;
  }

  public int getSets() {
    return sets;
  }

  public void setSets(int sets) {
    this.sets = sets;
  }

  public boolean isWantsThreeMore() {
    return wantsThreeMore;
  }

  public void setWantsThreeMore(boolean wantsThreeMore) {
    this.wantsThreeMore = wantsThreeMore;
  }

  public String getSessionId() {
    return sessionId;
  }

  public void setSessionId(String sessionId) {
    this.sessionId = sessionId;
  }
}
