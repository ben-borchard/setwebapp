// src/components/Card.js

import React, { Component } from 'react';
import { Grid, Row, Col } from 'react-bootstrap';

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

    // put all facets (include height into one object)
    const facets = { height: this.props.height, opacity: opacity, color: color };

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
    for (var i=0;i<number;i++) {
      shapes.push(shape);
    }
    return (
      <div onClick={this.select}>
        <Grid fluid="true" className={"setapp-card"+(this.state.selected ? " setapp-card-selected" : "")}>
          <Row className="setapp-card-content">
            { number === 2 && <Col md="2" /> }
            { number === 1 && <Col md="4" /> }
            {shapes.map(shapeInst => (
              <Col md={4} className="setapp-shape text-center center-block">
                {shapeInst}
              </Col>
            ))}
            { number === 2 && <Col md="2" /> }
            { number === 1 && <Col md="4" /> }
          </Row>
        </Grid>
      </div>
    )
  }


}

export default Card;
