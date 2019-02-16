package com.bborchard.setapp;

import com.bborchard.setapp.model.Card;

import java.util.List;
import java.util.Random;
import java.util.Stack;

public class SetUtils {

  public static Stack<Card> createDeck() {
    Stack<Card> deck = new Stack<>();
    for (Card.Shape shape : Card.Shape.values()) {
      for (Card.Color color : Card.Color.values()) {
        for (Card.Opacity opacity : Card.Opacity.values()) {
          for (Card.Number number : Card.Number.values()) {
            deck.add(new Card(shape, opacity, color, number));
          }
        }
      }
    }
    return deck;
  }

  public static Stack<Card> shuffleDeck(Stack<Card> deck) {
    Random rand = new Random();
    int size = deck.size();
    Stack<Card> shuffledDeck = new Stack<>();
    for (int i = 0; i < size; i++) {
      shuffledDeck.add(deck.remove(rand.nextInt(deck.size())));
    }
    return shuffledDeck;
  }

  /**
   * Brute way of finding a set in a board. Not sure if there is a better way - cost is not that high unless board is
   * unreasonably large
   * @return null if none exists
   */
  public static Integer[] findSet(List<Card> board) {
    for (int i = 0; i < board.size()-2; i++) {
      for (int j = i+1; j < board.size()-1; j++) {
        for (int k = j+1; k < board.size(); k++) {
          if (isSet(board.get(i), board.get(j), board.get(k))) {
            return new Integer[] {i,j,k};
          }
        }
      }
    }
    return null;
  }

  public static boolean hasSet(List<Card> board) {
    return findSet(board) != null;
  }

  public static boolean isSet(Card card1, Card card2, Card card3) {
    for (int i = 0; i < card1.getFacets().length; i++) {
      if ( !allSame(card1.getFacets()[i], card2.getFacets()[i], card3.getFacets()[i]) &&
           !allDifferent(card1.getFacets()[i], card2.getFacets()[i], card3.getFacets()[i]) ) {
        return false;
      }
    }
    return true;
  }

  private static boolean allSame(Enum enum1, Enum enum2, Enum enum3) {
    return enum1.equals(enum2) && enum2.equals(enum3); // commutative property
  }

  private static boolean allDifferent(Enum enum1, Enum enum2, Enum enum3) {
    return !enum1.equals(enum2) && !enum1.equals(enum3) && !enum2.equals(enum3);
  }

}
