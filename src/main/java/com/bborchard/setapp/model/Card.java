package com.bborchard.setapp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

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

  public enum Shape {
    OVAL,
    DIAMOND,
    SQUIGGLE
  }

  public enum Opacity {
    CLEAR,
    LINED,
    FILLED
  }

  public enum Color {
    PURPLE,
    GREEN,
    RED
  }

  public enum Number {
    ONE,
    TWO,
    THREE
  }

  @JsonIgnore
  public Enum[] getFacets() {
    return new Enum[] {shape, opacity, color, number};
  }

  public Shape getShape() {
    return shape;
  }

  public void setShape(Shape shape) {
    this.shape = shape;
  }

  public Opacity getOpacity() {
    return opacity;
  }

  public void setOpacity(Opacity opacity) {
    this.opacity = opacity;
  }

  public Color getColor() {
    return color;
  }

  public void setColor(Color color) {
    this.color = color;
  }

  public Number getNumber() {
    return number;
  }

  public void setNumber(Number number) {
    this.number = number;
  }
}
