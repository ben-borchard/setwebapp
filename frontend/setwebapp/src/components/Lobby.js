
// src/components/Home.js
import React, { Component } from 'react';
import { Link } from '@curi/react-dom';

import NewGameForm from './NewGameForm';

class Lobby extends Component {

  constructor(props) {
    super(props);
    
    this.state = {
      name: this.props.response.location.hash,
      error: null,
      games: []
    }

    this.componentDidMount = this.componentDidMount.bind(this);
    this.handleChange = this.handleChange.bind(this);
    this.handleSubmit = this.handleSubmit.bind(this);
    this.randomName = this.randomName.bind(this);
  }

  componentDidMount() {
    fetch("/getGames")
      .then(response => { return response.json() })
      .then(data => {
        this.setState({ games: data })
      });
  }

  handleChange(event) {
    this.name = event.target.value;
  }

  handleSubmit(event) {
    this.setState({
      name: this.name
    })
    event.preventDefault();
  }

  randomName() {
    fetch("/randomName", { method: "GET" })
      .then(response => {
        console.log(response);
        response.text().then(name => {
          console.log(name);
          this.setState({ name: name });
        });
      });
  }

  render() {
    const games = this.state.games;
    return (
      <div class="setapp-lobby-container">
        {this.state.name ? (
          <div>
            <h1>Welcome to the set webapp {this.state.name}!</h1>
            {games.length === 0 ? (
              <h3>There are no games currently, you should create a new one!</h3>
            ) : (
              <div>
                <h3>Select a game to join below</h3>
                <ul>
                  {games.map(game => (
                    <li key={game}>
                      <Link to="Game" hash={this.state.name} params={{ name: game }} >
                        {game}
                      </Link>
                    </li>
                  ))}
                </ul>
                <h3>Or create a new game</h3>
              </div>
            )}
            <NewGameForm name={this.state.name} router={this.props.router} />
            <button onClick={this.componentDidMount}>Refresh Game List</button>
          </div>
        ) : (
          <div>
            <h1>Welcome to the set webapp!</h1>
            <h3>Enter your name below:</h3>
            <form onSubmit={this.handleSubmit}>
              {this.state.error ? (
                <p>{this.state.error.message}</p>
                ) : null}
              <label>
                My Name:
                <input type="text" onChange={this.handleChange} />
              </label>
              <input type="submit" value="Choose name" />
            </form>
            <button onClick={this.randomName}>Give me a random name!!</button>
          </div>
        )}
      </div>
    );
  }
}

export default Lobby;
