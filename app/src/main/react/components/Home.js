import React, { Component } from 'react';
import '../styles/App.css';
import AppNavbar from './AppNavbar';
import EventsSearchBox from "./EventsSearchBox";
import EventsCluster from './EventsCluster';
import {Col, Row} from "reactstrap";
import { slide as Menu } from 'react-burger-menu'

class Home extends Component {

  constructor(props) {
    super(props);
    this.state = {hasSearches: false, eventsResponse: {}};

    this.handleEventsSearch = this.handleEventsSearch.bind(this);
    this.handleNewSearches = this.handleNewSearches.bind(this);
  }

  showSettings(event) {
    event.preventDefault();
  }

  handleEventsSearch(eventsResponse) {
    this.setState({hasSearches: true, eventsResponse: eventsResponse})
  }

  handleNewSearches() {
    this.setState({hasSearches: false})
  }

  render() {

    let modalEventsCluster, actualPage;
    if(this.state.hasSearches) {
      modalEventsCluster = <EventsCluster data={this.state.eventsResponse} />;
    } else {
      actualPage = 1;
    }

    return (
      <div>
        <AppNavbar/>

        <Menu>
          <a id="home" className="menu-item" href="/">Home</a>
          <a id="about" className="menu-item" href="/about">About</a>
          <a id="contact" className="menu-item" href="/contact">Contact</a>
          <a onClick={ this.showSettings } className="menu-item--small" href="">Settings</a>
        </Menu>

        <Row>
          <Col xs="3">
            <EventsSearchBox searchHandler={this.handleEventsSearch}
                             hasSearches={this.state.hasSearches}
                             handleNewSearches={this.handleNewSearches}
                             actualPage={actualPage}/>
          </Col>
          <Col sm="6">
            {modalEventsCluster}
          </Col>
        </Row>
      </div>
    );
  }

}

export default Home;