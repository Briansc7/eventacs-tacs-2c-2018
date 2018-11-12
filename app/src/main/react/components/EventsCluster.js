import React, {Component} from "react";
import EventsTable from "./EventsTable";
import Pages from "./Pages";
import {Container} from "reactstrap";

class EventsCluster extends Component {

  render () {
    return(
      <Container>
        <EventsTable data={this.props.data}/>
        <Pages metadata={this.props.data.data.metadata} changePage={this.props.changePage}/>
      </Container>
    );
  }

}

export default EventsCluster;