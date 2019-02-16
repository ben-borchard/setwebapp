package com.bborchard.setapp;

import org.junit.Test;

public class ManualTest {

  @Test
  public void test() throws Exception {

    Application.main(new String[0]);
    System.out.println("Started, press enter to finish");
    System.in.read();

  }
}