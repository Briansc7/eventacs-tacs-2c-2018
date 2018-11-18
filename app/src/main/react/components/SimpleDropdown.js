import React, { Component } from 'react';
import Select from "react-select";
import '../styles/SimpleDropdown.css';

export default class SimpleDropdown extends Component {

  constructor(props) {
    super(props);
    this.toggle = this.toggle.bind(this);
    this.state = {dropdownOpen: false, selectedOptions: []};
  }

  toggle() {
    this.setState(prevState => ({
      dropdownOpen: !prevState.dropdownOpen
    }));
  }

  handleSelected = (selectedOption) => {
      this.setState({selectedOptions: selectedOption});
      this.props.selectedHandler(selectedOption);
  };

  render() {
    const {selectedOptions} = this.state;

    return (
      <Select
        placeholder={this.props.header}
        isMulti
        name={this.props.header}
        value={selectedOptions}
        onChange={this.handleSelected}
        className={"basic-multi-select select-style"}
        classNamePrefix="select"
        options={this.props.data.map(c => {return {value: c.id, label: c.name}})}
      />
    );
  }

}