// src/components/Oval.js

import React, { Component } from 'react';

class Shape extends Component {

  getPath() {
    return "M10,110 l0,-60";
  }

  render() {

    const path = this.getPath();
    return (
      <svg height="100%" width="100%" viewBox="0 0 100 160">
        <path d={path} stroke={this.props.facets.color} 
                       stroke-width="2" 
                       fill={this.props.facets.color}
                       fill-opacity={this.props.facets.opacity}  />
      </svg>
    )
  }


}

export default Shape;
