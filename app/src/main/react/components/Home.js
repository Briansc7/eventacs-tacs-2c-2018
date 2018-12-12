import React, { Component } from 'react';
import '../styles/App.css';
import '../styles/css/style.css'
import AppNavbar from './AppNavbar';
import EventsSearchBox from "./EventsSearchBox";
import EventsCluster from './EventsCluster';
import {Col, Row, Button} from "reactstrap";
import './oauth.js';
import { slide as Menu } from 'react-burger-menu'

class Home extends Component {

  constructor(props) {
    super(props);
    this.state = {hasSearches: false, eventsResponse: {}, userLoggedIn: true, userData: {}, tokenAccess: ''};
    this.setState({tokenAccess: JSON.parse(localStorage.getItem("dataSession"))});
    this.setState({userData: JSON.parse(localStorage.getItem("dataSession")).principal})
    this.handleEventsSearch = this.handleEventsSearch.bind(this);
    this.handleNewSearches = this.handleNewSearches.bind(this);
    this.userLoginHandler = this.userLoginHandler.bind(this);
  }

  handleEventsSearch(eventsResponse) {
    this.setState({hasSearches: true, eventsResponse: eventsResponse})
  }

  handleNewSearches() {
    this.setState({hasSearches: false})
  }

  userLoginHandler(userData) {
    if(userData !== {}) {
      console.log(userData);
      this.setState({userLoggedIn: !this.state.userLoggedIn, userData: userData})
    }
  }

  logout() {
      fetch('http://oauth-server:9001/oauth-server/oauth/token/revokeById/'+JSON.parse(localStorage.getItem("dataSession")).access_token, {
          method: 'POST',})
          .then(response => response.json());
      localStorage.clear();
   //  this.props.dispatch(pushPath('/'));
  }

  render() {

    let modalEventsCluster, actualPage, modalEventSearchBox;

    if(this.state.hasSearches) {
      modalEventsCluster = <EventsCluster data={this.state.eventsResponse} />;
    } else {
      actualPage = 1;
    }

    if(this.state.userLoggedIn) {
      modalEventSearchBox = <EventsSearchBox searchHandler={this.handleEventsSearch}
                                             hasSearches={this.state.hasSearches}
                                             handleNewSearches={this.handleNewSearches}
                                             userEventLists={this.state.userData.eventLists}
                                             actualPage={actualPage}/>
    }

    return (
      <div>
        <AppNavbar userLoginHandler={this.userLoginHandler}/>
        <Row>
          <Col xs="3">
            {modalEventSearchBox}
          </Col>
          <Col sm="6">
            {modalEventsCluster}
          </Col>
            <Button onClick={this.logout}>
                Logout
            </Button>
        </Row>
      </div>
    );

  }

}

export default Home;