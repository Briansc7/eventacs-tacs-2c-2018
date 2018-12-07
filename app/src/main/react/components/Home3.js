import React from 'react';
import { TabContent, TabPane, Nav, NavItem, NavLink, Button, Row, Col, Container } from 'reactstrap';
import classnames from 'classnames';
import EventsSearchBox from "./EventsSearchBox";
import EventsCluster from "./EventsCluster";

export default class Example extends React.Component {
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

    render() {
        let actualPage, modalEventSearchBox;

        if(this.state.hasSearches) {
            this.state.modalEventsCluster = <EventsCluster data={this.state.eventsResponse} />;
        } else {
            actualPage = 1;
        }

        return (
                <Container>
                <Row>
<Col >

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
</Col>
                </Row>
<Row>
                    <Col>
                <TabContent activeTab={this.state.activeTab}>
                    <TabPane tabId="1">
                        <Row>
                            <Col sm="12" md={{ size: 12, offset: 0 }}>
                                <EventsSearchBox searchHandler={this.handleEventsSearch}
                                                                                      hasSearches={this.state.hasSearches}
                                                                                      handleNewSearches={this.handleNewSearches}
                                                                                      userEventLists={this.state.userData.eventLists}
                                                                                      actualPage={actualPage}/>
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
                    </Col>
                </Row>
                </Container>
        );
    }
}