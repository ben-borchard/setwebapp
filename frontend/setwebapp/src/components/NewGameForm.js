
// src/components/NewGameForm.js
import React, { Component } from 'react';

class NewGameForm extends Component {

  constructor(props) {
    super(props);
    this.state = { 
      name: '', 
      error: null }

    this.handleChange = this.handleChange.bind(this);
    this.handleSubmit = this.handleSubmit.bind(this);
  }

  handleChange(event) {
    this.setState({name: event.target.value});
  }

  handleSubmit(event) {
    fetch("/create?name="+this.state.name, { method: "POST" })
      .then(response => {
        if (response.ok) {
          this.props.router.navigate({ name: "Game", hash: this.props.name, params: {
            name: this.state.name
          }});
        } else {
          response.json().then(error => this.setState({ error: error }));
        }
      })
    event.preventDefault();
  }

  render() {
    return (
      <form onSubmit={this.handleSubmit}>
        {this.state.error ? (
          <p>{this.state.error.message}</p>
          ) : null}
        <label>
          Game Name:
          <input type="text" value={this.state.name} onChange={this.handleChange} />
        </label>
        <input type="submit" value="Create Game!" />
      </form>
    )
  }
}

export default NewGameForm;