// src/components/BoardRow.js

import React, { Component } from 'react';
import { Row, Col } from 'react-bootstrap';

import Card from './Card.js';

class BoardRow extends Component {

  render() {
    return (
      <Row>
        <Col xs={4} sm={4} md={4} lg={4}>
          <Card select={this.props.cardSelected} 
                card={this.props.cards[0].card} 
                index={this.props.cards[0].index} 
                height={this.props.height} 
          />
        </Col>
        <Col xs={4} sm={4} md={4} lg={4}>
          <Card select={this.props.cardSelected} 
                card={this.props.cards[1].card} 
                index={this.props.cards[1].index} 
                height={this.props.height} 
          />
        </Col>
        <Col xs={4} sm={4} md={4} lg={4}>
          <Card select={this.props.cardSelected} 
                card={this.props.cards[2].card} 
                index={this.props.cards[2].index} 
                height={this.props.height} 
          />
        </Col>
      </Row>
    )
  }


}

export default BoardRow;
