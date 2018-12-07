import React, { Component } from 'react';
import PropTypes from 'prop-types';
import '../styles/App.css';
import classnames from 'classnames';
import AppNavbar from './AppNavbar';
import EventsTable from './EventsTable';
import { Button, TabContent, TabPane, Nav, NavItem, NavLink, Row, Col } from 'reactstrap';
import { withStyles } from '@material-ui/core/styles';
import EventsSearchBox from "./EventsSearchBox";
import EventsCluster from './EventsCluster';
import Grid from '@material-ui/core/Grid';
import './oauth.js';
import { slide as Menu } from 'react-burger-menu'
import NavbarBrand from "reactstrap/src/NavbarBrand";
import {Link} from "react-router-dom";
import NavbarToggler from "reactstrap/src/NavbarToggler";
import Collapse from "reactstrap/src/Collapse";
import Navbar from "reactstrap/src/Navbar";

const styles = theme => ({
    root: {
        flexGrow: 1,
    },
});

class Home extends Component {

    constructor(props) {
        super(props);
        this.state = {activeTab: '1', hasSearches: false, eventsResponse: {}, userLoggedIn: true, userData: {}};

        this.handleEventsSearch = this.handleEventsSearch.bind(this);
        this.handleNewSearches = this.handleNewSearches.bind(this);
        this.userLoginHandler = this.userLoginHandler.bind(this);
       // this.modificar = this.modificar.bind(this);
        this.toggle = this.toggle.bind(this);
    }

    toggle(tab) {
        if (this.state.activeTab !== tab) {
            this.setState({
                activeTab: tab
            });
        }
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

    // modificar() {
    //     this.setState({modalEventSearchBox: '<EventsTable></EventsTable>'});
    // }

    render() {

        let modalEventsCluster, actualPage, modalEventSearchBox;
        //let actualPage, modalEventSearchBox;

        if(this.state.hasSearches) {
            this.state.modalEventsCluster = <EventsCluster data={this.state.eventsResponse} />;
        } else {
            actualPage = 1;
        }

        // if(this.state.userLoggedIn) {
        //     modalEventSearchBox = <EventsSearchBox searchHandler={this.handleEventsSearch}
        //                                                       hasSearches={this.state.hasSearches}
        //                                                       handleNewSearches={this.handleNewSearches}
        //                                                       userEventLists={this.state.userData.eventLists}
        //                                                       actualPage={actualPage}/>
        // }
        const { classes } = this.props;

        return (
            <div>
                        <Navbar color="dark" dark expand="md">
                            <NavbarBrand tag={Link} to="/Home">Home</NavbarBrand>
                            <NavbarToggler onClick={this.toggle}/>
                            <Collapse isOpen={this.state.isOpen} navbar>
                                <Nav tabs>
                                    <NavItem>
                                        <NavLink
                                            className={classnames({ active: this.state.activeTab === '1' })}
                                            onClick={() => { this.toggle('1'); }}
                                        >
                                            Tab1
                                        </NavLink>
                                    </NavItem>
                                    <NavItem>
                                        <NavLink
                                            className={classnames({ active: this.state.activeTab === '2' })}
                                            onClick={() => { this.toggle('2'); }}
                                        >
                                            Moar Tabs
                                        </NavLink>
                                    </NavItem>
                                </Nav>
                            </Collapse>
                        </Navbar>


                        <TabContent activeTab={this.state.activeTab}>
                            <TabPane tabId="1">
                                <Row>
                                    <Col sm="12">
                                        {/*{modalEventSearchBox}*/}
                                    </Col>
                                </Row>
                            </TabPane>
                            <TabPane tabId="2">
                                <Row>
                                    <Col sm="6">
                                            <Button>Go somewhere</Button>
                                    </Col>
                                </Row>
                            </TabPane>
                        </TabContent>

                    {/*<div className={classes.root}>*/}
                    {/*<Grid>*/}
                    {/*<Grid>*/}
                    {/*<AppNavbar userLoginHandler={this.userLoginHandler}/>*/}
                    {/*</Grid>*/}
                    {/*<Grid item xs={6}>*/}

                    {/*{this.state.modalEventSearchBox}*/}
                    {/*</Grid>*/}
                    {/*<Grid item xs={6}>*/}
                    {/*<Button onClick={this.modificar()}>Aca</Button>*/}
                    {/*/!*{modalEventsCluster}*!/*/}
                    {/*</Grid>*/}
                    {/*</Grid>*/}
                    {/*</div>*/}
            </div>
    );

    }

    }


    export default Home;