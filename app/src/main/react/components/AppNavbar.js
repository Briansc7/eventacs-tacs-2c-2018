import React, { Component } from 'react';
import { Collapse, Nav, Navbar, NavbarBrand, NavbarToggler, NavItem, NavLink } from 'reactstrap';
import { Link } from 'react-router-dom';
//import LoginForm from "./LoginForm";
//import SigninForm from "./SigninForm";
import axios from "axios";

export default class AppNavbar extends Component {

  constructor(props) {
    super(props);
    this.state = {isOpen: false, isLoggedIn: false, toggleLoginForm: false, toggleSigninForm: false};
    // this.signinData = {username: "", password: ""};
    // this.toggle = this.toggle.bind(this);
    // this.signin = this.signin.bind(this);
    // this.login = this.login.bind(this);
    // this.logout = this.logout.bind(this);
 //   this.toggleLoginForm = this.toggleLoginForm.bind(this);
 //   this.toggleSigninForm = this.toggleSigninForm.bind(this);
 //   this.onLoginForm = this.onLoginForm.bind(this);
//    this.onSigninForm = this.onSigninForm.bind(this);
  }

  toggle() {
    this.setState({
      isOpen: !this.state.isOpen,
//      isLoggedIn: this.state.isLoggedIn
    });
  }

  // onLoginForm(username, password) {
  //   this.login({username: username, password: password})
  // };
  //
  // onSigninForm = (username, password) => {
  //   this.signinData = {username, password}
  // };
  //
  // async signin(signinData) {
  //   await fetch(`/signin`, {
  //     method: 'POST',
  //     headers: {
  //       'Accept': 'application/json',
  //       'Content-Type': 'application/json'
  //     },
  //     body: JSON.stringify(signinData),
  //   }).then(() => {
  //
  //   });
  // }

  // login(loginData) {
  //   axios.post('/eventacs/login',
  //               {name: loginData.username, encryptedPassword: loginData.password},
  //               {headers: {
  //                 'Accept': 'application/json',
  //                 'Content-Type': 'application/json'}
  //               })
  //     .then(response => {
  //       this.props.userLoginHandler(response.data)
  //     })
  //     .catch(error => {
  //       console.log(error)
  //     })
  // }
  //
  // async logout(logoutData) {
  //   await fetch(`/logout`, {
  //     method: 'POST',
  //     headers: {
  //       'Accept': 'application/json',
  //       'Content-Type': 'application/json'
  //     },
  //     body: JSON.stringify(logoutData),
  //   }).then(() => {
  //
  //   });
  // }

  // toggleLoginForm() {
  //   this.setState({toggleLoginForm: !this.state.toggleLoginForm});
  // }
  //
  // toggleSigninForm() {
  //   this.setState({toggleSigninForm: !this.state.toggleSigninForm, toggleLoginForm: this.state.toggleLoginForm});
  // }

  render() {
    let modalLogin, modalSignin;

    // if(this.state.toggleLoginForm) { modalLogin = <LoginForm loginHandler={this.onLoginForm}/>; }
    // if(this.state.toggleSigninForm) { modalSignin = <SigninForm signinHandler={this.onSigninForm}/>; }

    return (
      <Navbar color="dark" dark expand="md">
        <NavbarBrand tag={Link} to="/">Home</NavbarBrand>
        <NavbarToggler onClick={this.toggle}/>
        <Collapse isOpen={this.state.isOpen} navbar>
          <Nav className="ml-auto" navbar>
            <NavItem>
              {/*<NavLink onClick={() => this.toggleLoginForm()}>Login</NavLink>*/}
            </NavItem>
            <NavItem>
              {/*<NavLink onClick={() => this.toggleSigninForm()}>Signin</NavLink>*/}
            </NavItem>
          </Nav>
        </Collapse>
        {modalLogin}
        {modalSignin}
      </Navbar>
    );
  }

}