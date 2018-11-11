import React, { Component } from 'react';
import { Button, Form, FormGroup, Label, Input } from 'reactstrap';

export default class LoginForm extends Component {

  constructor() {
    super();
    this.state = {username: "", password: ""};

    this.handleLoginData = this.handleLoginData.bind(this);
    this.updateUsername = this.updateUsername.bind(this);
    this.updatePassword = this.updatePassword.bind(this);
  }

  updateUsername = event => {this.setState({username: event.target.value})};
  updatePassword = event => {this.setState({password: event.target.value})};

  handleLoginData = () => {this.props.loginHandler(this.state.username, this.state.password)};

  render() {
    return (
      <Form inline>
        <FormGroup className="mb-2 mr-sm-2 mb-sm-0">
          <Label for="username" className="mr-sm-2" hidden>Username</Label>
          <Input type="user" name="user" id="username" placeholder="Username" bsSize="sm" onChange={this.updateUsername}/>
        </FormGroup>
        <FormGroup className="mb-2 mr-sm-2 mb-sm-0">
          <Label for="password" className="mr-sm-2" hidden>Password</Label>
          <Input type="password" name="password" id="password" placeholder="Password" bsSize="sm" onChange={this.updatePassword}/>
        </FormGroup>
        <Button onClick={this.handleLoginData}>Login</Button>
      </Form>
    );
  }

}