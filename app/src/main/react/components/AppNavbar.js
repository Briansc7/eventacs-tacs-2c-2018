import React, { Component } from 'react';
import { Collapse, Navbar, NavbarBrand, NavbarToggler, TabContent, TabPane, Nav, NavItem, NavLink, Card, Button, CardTitle, CardText, Row, Col } from 'reactstrap';
import { Link } from 'react-router-dom';
import classnames from 'classnames';
import axios from "axios";

export default class AppNavbar extends Component {

  constructor(props) {
    super(props);
    this.state = {isOpen: false, isLoggedIn: false, toggleLoginForm: false, toggleSigninForm: false, activeTab: '1'};
      this.toggle = this.toggle.bind(this);

  }

  toggle() {
    this.setState({
      isOpen: !this.state.isOpen,
//      isLoggedIn: this.state.isLoggedIn
    });
  }

      toggleTabs(tab) {
          if (this.state.activeTab !== tab) {
              this.setState({
                  activeTab: tab
              });
          }
      }

    logout() {
        fetch('http://eventacs.com:9001/oauth-server/oauth/token/revokeById/'+JSON.parse(localStorage.getItem("dataSession")).access_token, {
            method: 'POST',})
            .then(response => response.json());
        localStorage.clear();
    }

  render() {

    return (
      <Navbar color="dark" dark expand="md">
        <NavbarBrand tag={Link} to="/Home">Home</NavbarBrand>
        <NavbarToggler onClick={this.toggle}/>
        <Collapse isOpen={this.state.isOpen} navbar>
          <Nav className="ml-auto" navbar>
              <NavItem>
                  <NavLink>{JSON.parse(localStorage.getItem("dataSession")).principal.name}</NavLink>
              </NavItem>

            <NavItem>
              <NavLink href="/" onClick={() => this.logout()}>Logout</NavLink>
            </NavItem>
          </Nav>

        </Collapse>
      </Navbar>
    );
  }
}