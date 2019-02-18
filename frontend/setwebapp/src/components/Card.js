// src/components/Card.js

import React, { Component } from 'react';

import Oval from './Oval.js'
import Diamond from './Diamond.js'
import Squiggle from './Squiggle.js'

class Card extends Component {
  
  constructor(props) {
    super(props)

    this.state = {
      selected: false
    }

    this.select = this.select.bind(this);
  }

  static getDerivedStateFromProps(nextProps, prevState) {
    if (!nextProps.select) {
      return {
        selected: false
      }
    }
  }

  select(card) {
    if (this.props.select) {
      this.setState({
        selected : !this.state.selected
      });
      this.props.select(this.props.index);
    }
  }

  render() {

    // color
    let color = "red"
    if (this.props.card.color === 'PURPLE') {
      color = "indigo";
    } else if (this.props.card.color === 'GREEN') {
      color = "green";
    }

    // opacity
    let opacity = "0";
    if (this.props.card.opacity === 'LINED') {
      opacity = ".2";
    } if (this.props.card.opacity === 'FILLED') {
      opacity = "1";
    }

    // put all facets
    const facets = { opacity: opacity, color: color };

    // shape
    let shape;
    if (this.props.card.shape === 'OVAL') {
      shape = <Oval facets={facets} />;
    } else if (this.props.card.shape === 'DIAMOND') {
      shape = <Diamond facets={facets} />;
    } else {
      shape = <Squiggle facets={facets} />;
    }

    // number
    const number = this.props.card.number === 'ONE' ? 1 : this.props.card.number === 'TWO' ? 2 : 3;
    const shapes = [];
    let columnTemplate = ""
    for (var i=0;i<number;i++) {
      shapes.push(shape);
      columnTemplate += "auto "
    }
    return (
      <div onClick={this.select} 
           className={"setapp-card"+(this.state.selected ? " setapp-card-selected" : "")}
           style={{"grid-template-columns": columnTemplate}}>
        {shapes.map(shapeInst => (
          <React.Fragment>
            {shapeInst}
          </React.Fragment>
        ))}
      </div>
    )
  }


}

export default Card;
