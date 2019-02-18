// src/components/Board.js

import React, { Component } from 'react';

import Card from './Card.js';

class Board extends Component {

  render() {
    // dynamic rows - auto size to fit in screen
    let gridTemplateRows = "";
    for (var i = 0; i < this.props.cards.length / 3; i++) {
      gridTemplateRows += "auto "
    }
    return (
      <div class="setapp-game-board-grid" style={{"grid-template-rows": gridTemplateRows}}>
        {this.props.cards.map(card => (
          <Card select={this.props.cardSelected} 
                card={card.card} 
                index={card.index} 
          />
        ))}
      </div>
    )
  }


}

export default Board;
