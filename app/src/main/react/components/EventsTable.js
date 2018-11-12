import React, { Component } from 'react';
import { Button, ButtonGroup, Container, Table } from 'reactstrap';
import { Link } from 'react-router-dom';
import SimpleDropdown from "./SimpleDropdown";

const tableStyle = {
  width: '100%'
};

class EventsTable extends Component {

  constructor(props) {
    super(props);
    this.state = {events: [], metadata: {}, userEventLists: [], isLoading: true};
  }

  componentDidMount() {
    this.setState({events: this.props.data.data.events,
                   metadata: this.props.data.data.metadata,
                   userEventLists: this.getUserEventLists,
                   isLoading: false});
  }

  handleSelectedEventListAction() {

  }

  render() {
    const {events, metadata, isLoading} = this.state;

    if (isLoading) {
      return <p>Loading...</p>;
    }

    if (events.length === 0) {
      return <p>No events found</p>;
    }

    const eventsData = events.map(event => {
      return(
        <tr key={event.id}>
          <td style={{whiteSpace: 'nowrap'}}>{event.name}</td>
          <td>{event.category}</td>
          <td>{event.description}</td>
          <td>{event.start}</td>
          <td>{event.end}</td>
          <td>
            <ButtonGroup>
              <Button size="sm" color="primary" tag={Link} to={"/event-lists/add/" + event.id}>Add to an EventList</Button>
            </ButtonGroup>
          </td>
        </tr>
      );
    });

    return (
      <div>
        <Container>

          <h3>Events Found: {metadata.objectCount}</h3>

          <Table className="mt-4" style={tableStyle}>
            <thead>
            <tr>
              <th width="20%">Name</th>
              <th width="20%">Category</th>
              <th width="20%">Description</th>
              <th width="20%">Start</th>
              <th width="20%">End</th>
              <th width="10%">Actions</th>
            </tr>
            </thead>
            <tbody>
            {eventsData}
            </tbody>
          </Table>

        </Container>
      </div>
    );
  }

}

export default EventsTable;
