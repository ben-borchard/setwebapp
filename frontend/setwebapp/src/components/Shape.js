// src/components/Oval.js

import React, { Component } from 'react';

class Shape extends Component {

  getPath() {
    return "M10,110 l0,-60";
  }

  render() {

    // convert height to use viewport units
    const height = this.props.facets.height + "vh";
    const path = this.getPath();
    return (
      <svg height={height} viewBox="0 0 100 160">
        <path d={path} stroke={this.props.facets.color} 
                       stroke-width="2" 
                       fill={this.props.facets.color}
                       fill-opacity={this.props.facets.opacity}  />
      </svg>
    )
  }


}

export default Shape;
