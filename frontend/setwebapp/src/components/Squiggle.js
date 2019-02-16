// src/components/Squiggle.js

import Shape from './Shape.js';

class Squiggle extends Shape {

  getPath() {
    return "M20,20 C40,45 40,45 20,70 S0,120 20,140 S80,150 80,140 S50,110 80,80 S90,20 80,10 S50,0 20,20"
  }

}

export default Squiggle;
