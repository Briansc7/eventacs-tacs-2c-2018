import React, {Component} from "react";
import SimpleDropdown from "./SimpleDropdown";
import {Button, Container, Input} from 'reactstrap';
import '../styles/EventsSearchBox.css';
import DatePicker from "./DatePicker";
import axios from 'axios';

const containerStyle = {
  width: '100%'
};

const marginSmStyle = {
  margin: '3px'
};

class EventsSearchBox extends Component {

  constructor(props) {
    super(props);
    this.state = {categories: [],
                  isLoading: true,
                  selectedKeyword: "",
                  selectedCategories: [],
                  selectedStartDate: "",
                  selectedEndDate: "",
                  requieredStartDate: false,
                  requieredEndDate: false};

    this.handleKeyword = this.handleKeyword.bind(this);
    this.handleSelectedCategories = this.handleSelectedCategories.bind(this);
    this.handleSelectedStartDate = this.handleSelectedStartDate.bind(this);
    this.handleSelectedEndDate = this.handleSelectedEndDate.bind(this);
    this.getEvents = this.getEvents.bind(this);
    this.prepareEventsUrl = this.prepareEventsUrl.bind(this);
  }

  componentWillMount() {
    this.setState({isLoading: true});
    let headers = {"content-Type": "application/x-www-form-urlencoded; charset=utf-8"};
    var token = JSON.parse(localStorage.getItem("dataSession"));
    headers["Authorization"] = `Bearer ${token.access_token}`;
    fetch('/eventacs/categories', {headers,})
    //fetch('/eventacs/categories')
      .then(response => response.json())
      .then(data => this.setState({categories: data, isLoading: false}));
  }

  handleSelectedCategories(selected) {
    this.setState({selectedCategories: selected});
  }

  handleSelectedStartDate(selected) {
    this.setState({selectedStartDate: selected.date.toISOString().split('T')[0]});
  }

  handleSelectedEndDate(selected) {
    this.setState({selectedEndDate: selected.date.toISOString().split('T')[0]});
  }

  handleKeyword = e => {
    this.setState({selectedKeyword: e.currentTarget.value});
  };

  getEvents() {
    if(this.props.hasSearches) { this.props.handleNewSearches() }

    axios.get(this.prepareEventsUrl())
      .then(response => this.props.searchHandler(response));
  }

  prepareEventsUrl() {
    const basePath = '/eventacs/events?';

    const parameters = [`keyword=${this.state.selectedKeyword}`,
                        `startDate=${this.state.selectedStartDate}`,
                        `endDate=${this.state.selectedEndDate}`,
                        `categories=${this.state.selectedCategories.map(c => c.value).join(',')}`,
                        `page=${this.props.actualPage}`].join('&');

    return basePath.concat(parameters);
  }

  render() {
    return (
      <Container style={containerStyle}>
        <Input style={marginSmStyle} placeholder="Keyword" onChange={this.handleKeyword}/>
        <SimpleDropdown data={this.state.categories} header={"Categories"} selectedHandler={this.handleSelectedCategories}/>
        <DatePicker header={"Start Date"} selectedHandler={this.handleSelectedStartDate}/>
        <DatePicker header={"End Date"} selectedHandler={this.handleSelectedEndDate}/>
        <Button color="primary" className={"button-style"} onClick={this.getEvents}>Search</Button>{' '}
      </Container>
    );
  }

}

export default EventsSearchBox;