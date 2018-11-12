import React, { Component } from 'react';
import { Button, Form, FormGroup, Label, Input } from 'reactstrap';

export default class SigninForm extends Component {
  render() {
    return (
      <Form inline>
        <FormGroup className="mb-2 mr-sm-2 mb-sm-0">
          <Label for="username" className="mr-sm-2" hidden>Username</Label>
          <Input type="user" name="user" id="username" placeholder="Username" bsSize="sm"/>
        </FormGroup>
        <Button>Signin</Button>
      </Form>
    );
  }
}