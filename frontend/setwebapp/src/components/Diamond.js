// src/components/Diamond.js

import Shape from './Shape.js'

class Diamond extends Shape {

  getPath() {
    return "M10,80 l40,70 l40,-70 l-40,-70 Z";
  }
  
}

export default Diamond;
