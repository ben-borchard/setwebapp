// src/components/Oval.js

import Shape from './Shape.js'

class Oval extends Shape {

  getPath() {
    return "M10,110 a1,1 0 0,0 80,0 l0,-60 a1,1 0 0,0 -80,0 Z";
  }

}

export default Oval;
