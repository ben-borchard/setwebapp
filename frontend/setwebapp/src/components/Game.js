// src/components/Game.js
import SockJS from 'sockjs-client';
import Stomp from 'stomp-websocket';

import React, { Component } from 'react';
import { Button } from 'react-bootstrap';
import Popup from 'reactjs-popup';

import Board from './Board.js'

class Game extends Component {

  constructor(props) {
    super(props);

    // keep track of session id so we know who we are (even if someone else has our name)
    const sessionId = Math.random().toString(36).substring(8);
    var sock = new SockJS('/game/connect', [], {
      sessionId: () => {
        return sessionId;
      }
    });
    this.stomp = Stomp.over(sock);

    this.state = { 
      name: props.response.params.name, 
      sessionId: sessionId,
      game: undefined
    };

    this.addThree = this.addThree.bind(this);
    this.foundSet = this.foundSet.bind(this);
    this.cardSelected = this.cardSelected.bind(this);
    this.findSet = this.findSet.bind(this);
    this.leaveGame = this.leaveGame.bind(this);

    this.selected = [];
  }

  componentDidMount() {

    this.stomp.connect({}, (frame) => {
      this.stomp.subscribe('/state/'+this.state.name, (response) => {
        this.setState({ game: JSON.parse(response.body) });
      }, {"game": this.state.name});

      this.stomp.send('/update/' + this.state.name + '/join', {}, this.props.response.location.hash);
    });
  }

  addThree(event) {
    this.stomp.send('/update/' + this.state.name + '/addThree');
  }

  foundSet(event) {
    this.stomp.send('/update/' + this.state.name + '/foundSet');
  }

  cardSelected(index) {
    const i = this.selected.indexOf(index);
    if (i === -1) {
      // new index - add it
      console.log("card selected: "+index+", total: "+this.selected.length);
      this.selected.push(index);
      if (this.selected.length === 3) {
        this.stomp.send('/update/' + this.state.name + '/submitSet', {}, JSON.stringify(this.selected));
      }
    } else {
      // existing index - remove it
      this.selected.splice(i, 1);
    }
  }

  leaveGame() {
    // manually disconnect because we are routing back
    this.stomp.disconnect();
    this.props.router.navigate({ name: "Lobby", hash: this.props.response.location.hash });
  }

  // this is just cheating/ease of testing
  findSet(event) {
    this.stomp.send('/update/' + this.state.name + '/findSet');
  }


  render() {
    // ease of access
    const game = this.state.game;

    // whether or not we are selecting a set
    let cardSelected = false;

    // info text
    let info = "Once you find a set click the 'SET' button";

    // cards anotated with index
    let boardCards = [];

    // whether to disable the request three more button
    let canRequestThreeMore = false;

    // whether to disable the found set button
    let canFindSet = true;
    
    // users as jsx
    let renderedUsers = [];

    // number of sets current user has
    let userSets = 0;
    
    // initialize a loop variable
    let i = 0;

    // background color
    let bgColor = "lightblue"

    if (game) {

      // if someone is looking for a set
      if (game.userFindingSet) {
        canFindSet = false;
        // if this user is looking for a set
        if (game.userFindingSet.sessionId === this.state.sessionId) {
          bgColor = "lightgreen";
          cardSelected = this.cardSelected;
          info = (<div>You have <b>{game.timer}</b> seconds left to submit a set</div>);
        } else {
          bgColor = "lightcoral";
          info = (<div>User <b>{game.userFindingSet.name}</b> has <b>{game.timer}</b> seconds left to submit a set</div>);
        }
      }

      // wrap cards
      for (i = 0; i < game.board.length; i++) {
        boardCards.push({
          index: i,  // attach the index so card can pass it back when selected
          card: game.board[i]
        })
      }

      // three more possible?
      canRequestThreeMore = game.deck !== 0;

      // get users 
      for (i = 0; i < game.users.length; i++) {
        const user = game.users[i];
        // base
        let userJsx = (<span>{user.name} : {user.sets}</span>);
        // current user?
        if (user.sessionId === this.state.sessionId) {
          userSets = user.sets;
          userJsx = (<b>{userJsx} (you)</b>);
        }
        // voted for three more?
        if (user.wantsThreeMore) {
          userJsx = (<span>{userJsx} (wants three more)</span>);
          if (user.sessionId === this.state.sessionId) {
            // a little housekeeping - disable request three more button
            canRequestThreeMore = false;
          }
        }
        renderedUsers.push(userJsx);
      }

      // if the game is over
      if (game.winner) {
        canRequestThreeMore = false;
        canFindSet = false;
        if (game.winner.sessionId === this.state.sessionId) {
          info =  (<h3>YOU WON!!!</h3>)
        } else {
          info =  (<h3>The game is over<br></br>
                       {game.winner.name} won. :( </h3>)
        }
      }

      // reset selected if not selecting
      if (!cardSelected) {
        this.selected.length = 0;
      }
    }

    return (
      <div class="setapp-game" style={{"background-color": bgColor}}>
        <div class="setapp-game-title">Game: {this.state.name}</div>
          {game ? (
            <React.Fragment>
              <div class="setapp-game-setbutton">
                {canFindSet ? (
                  <p><Button onClick={this.foundSet}>I FOUND A SET!</Button></p>
                ) : (
                  <React.Fragment>{ info }</React.Fragment>
                )}
              </div>
              <div class="setapp-game-setnum">
                You have {userSets} sets
              </div>
              <div class="setapp-game-requestthree">
                {canRequestThreeMore ? (
                  <p><Button onClick={this.addThree}>Request Three More</Button></p>
                ) : (
                  <p><Button disabled>Request Three More</Button></p>
                )}
                
              </div>
              <div class="setapp-game-infobutton">
                <Popup trigger={<Button>Info</Button>} modal>
                  <div class="setapp-game-info" >
                    <p>Players in game:</p>
                    <ul>
                      {renderedUsers.map(user => (
                        <li>
                          {user}
                        </li>
                      ))}
                    </ul>
                    <p>Cards left in deck: {game.deck}</p>
                    <p>Players requesting three: {game.addThreeVotes}</p>
                    <p><Button onClick={this.leaveGame}>Leave Game</Button></p>
                    <p><Button onClick={this.findSet} style={{ display: "none" }}>Find Set</Button></p>
                  </div>
                </Popup>
              </div>
              <div class="setapp-game-board">
                <Board cardSelected={cardSelected} cards={boardCards} />
              </div>
            </React.Fragment>
          ) : (
            <div class="setapp-game-loading">Loading...</div>
          )}
      </div>
    )
  }


}

export default Game;
