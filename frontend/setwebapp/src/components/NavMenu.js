
// src/components/NavMenu.js
import React from 'react';
import { Link } from '@curi/react-dom';

export default function NavMenu() {
  return (
    <nav>
      <ul>
        <li>
          <Link to="Home">Home</Link>
        </li>
        <li>
          <Link to="Checkout">Checkout</Link>
        </li>
      </ul>
    </nav>

  );
}