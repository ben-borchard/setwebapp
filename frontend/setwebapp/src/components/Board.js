// src/components/Board.js

import React, { Component } from 'react';
import { Grid } from 'react-bootstrap';

import BoardRow from './BoardRow.js';

class Board extends Component {

  render() {
    const rowLength = 3;
    // determine height (in viewport units) to pass through to Shape component
    const numRows = this.props.cards.length / rowLength;
    const boardHeight = 92; // board height is not full view height (8% is header)
    const marginPerRow = 6; // account for the margins in each row (we are sizing the shapes, not rows) - estimate
    const maxHeight = 33 - marginPerRow; // maximum vh height that doesn't mess with the svg ratios
    const height = Math.min((boardHeight / numRows) - marginPerRow, maxHeight);

    // make rows
    var rows = [];
    for (var i = 0; i < this.props.cards.length; i += rowLength) {
      rows.push(<BoardRow cardSelected={this.props.cardSelected} 
                          height={height} 
                          cards={this.props.cards.slice(i, i+rowLength)}
                />)
    }

    return (
      <Grid fluid="true">
        {rows.map(row => (
          <div>
            {row}
          </div>
        ))}
      </Grid>
    )
  }


}

export default Board;
