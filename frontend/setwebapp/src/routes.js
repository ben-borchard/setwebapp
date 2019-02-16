
// src/routes.js
import Game from './components/Game';
import Lobby from './components/Lobby';
import NotFound from './components/NotFound';

export default [
  {
    name: "Lobby",
    path: "/",
    response() {
      return {
        body: Lobby
      };
    }
  },
  {
    name: "Game",
    path: "game/:name",
    response() {
      return {
        body: Game
      };
    }
  },
  {
    name: "Catch All",
    path: "(.*)",
    response() {
      return {
        body: NotFound
      };
    }
  }
]