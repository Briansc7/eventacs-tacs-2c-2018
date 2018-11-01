import React, { Component } from 'react';
import { Button, Form, FormGroup, Label, Input } from 'reactstrap';

export default class LoginForm extends Component {

  constructor() {
    super();
    this.handleLoginData = this.handleLoginData.bind(this);
    this.usernameInputRef = React.createRef();
    this.passwordInputRef = React.createRef();
  }

  handleLoginData = () => {this.props.loginHandler(this.usernameInputRef.value, this.passwordInputRef.value)};

  render() {
    return (
      <Form inline>
        <FormGroup className="mb-2 mr-sm-2 mb-sm-0">
          <Label for="username" className="mr-sm-2" hidden>Username</Label>
          <Input type="user" name="user" id="username" placeholder="Username" bsSize="sm" ref={this.usernameInputRef}/>
        </FormGroup>
        <FormGroup className="mb-2 mr-sm-2 mb-sm-0">
          <Label for="password" className="mr-sm-2" hidden>Password</Label>
          <Input type="password" name="password" id="password" placeholder="Password" bsSize="sm" ref={this.passwordInputRef}/>
        </FormGroup>
        <Button onClick={this.handleLoginData}>Login</Button>
      </Form>
    );
  }

}