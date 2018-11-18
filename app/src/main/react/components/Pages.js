import React from 'react';
import { Pagination, PaginationItem, PaginationLink } from 'reactstrap';

const paginationStyle = {
  width: '100%'
};

export default class Pages extends React.Component {

  constructor(props) {
    super(props);
    this.state = {metadata: {}};
  }

  componentDidMount() {
    this.setState({metadata: this.props.metadata});
  }

  render() {

    const {metadata} = this.state;

    return (

      <Pagination style={paginationStyle}>

        <PaginationItem>
          <PaginationLink previous href="#" />
        </PaginationItem>

        <PaginationItem>
          <PaginationLink href="#">
            1
          </PaginationLink>
        </PaginationItem>

        <PaginationItem>
          <PaginationLink next href="#" />
        </PaginationItem>
      </Pagination>
    );

  }
}