package com.bborchard.setapp.Model;

public class Card {

  public Shape shape;
  public Opacity opacity;
  public Color color;
  public Number number;

  public Card() {}

  public Card(Shape shape, Opacity opacity, Color color, Number number) {
    this.shape = shape;
    this.opacity = opacity;
    this.color = color;
    this.number = number;
  }

  public static enum Shape {
    OVAL,
    DIAMOND,
    SQUIGGLE
  }

  public static enum Opacity {
    CLEAR,
    LINED,
    FILLED
  }

  public static enum Color {
    PURPLE,
    GREEN,
    RED
  }

  public static enum Number {
    ONE,
    TWO,
    THREE
  }
}
