import React, { Component } from 'react';
import '../styles/App.css';
import '../styles/css/style.css'
import AppNavbar from './AppNavbar';
import EventsSearchBox from "./EventsSearchBox";
import EventsCluster from './EventsCluster';
import {Col, Row, Button} from "reactstrap";
import Grid from '@material-ui/core/Grid';
import './oauth.js';
import { slide as Menu } from 'react-burger-menu'
import PropTypes from 'prop-types';
import { withStyles } from '@material-ui/core/styles';
import AppBar from '@material-ui/core/AppBar';
import Tabs from '@material-ui/core/Tabs';
import NoSsr from '@material-ui/core/NoSsr';
import Tab from '@material-ui/core/Tab';
import Typography from '@material-ui/core/Typography';

function TabContainer(props) {
    return (
        <Typography component="div" style={{ padding: 8 * 3 }}>
            {props.children}
        </Typography>
    );
}

TabContainer.propTypes = {
    children: PropTypes.node.isRequired,
};

function LinkTab(props) {
    return <Tab component="a" onClick={event => event.preventDefault()} {...props} />;
}

const styles = theme => ({
    root: {
        flexGrow: 1,
        backgroundColor: theme.palette.background.paper,
    },
});
class Home extends Component {

  constructor(props) {
    super(props);
    this.state = {hasSearches: false, eventsResponse: {}, userLoggedIn: true, userData: {}, tokenAccess: '', value: 0};
    this.setState({tokenAccess: JSON.parse(localStorage.getItem("dataSession"))});
    this.setState({userData: JSON.parse(localStorage.getItem("dataSession")).principal})
    this.handleEventsSearch = this.handleEventsSearch.bind(this);
    this.handleNewSearches = this.handleNewSearches.bind(this);
    this.userLoginHandler = this.userLoginHandler.bind(this);
  }



    handleChange = (event, value) => {
        this.setState({ value });
    };

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

      const { classes } = this.props;
      const { value } = this.state;
    let modalEventsCluster, actualPage, modalEventSearchBox, headerTabs;

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
        <Grid>
        <Grid>
        <AppNavbar userLoginHandler={this.userLoginHandler}/>
        </Grid>
          <Grid>
              <NoSsr>
                  <div className={classes.root}>
                      <AppBar position="static">
                          <Tabs fullWidth value={value} onChange={this.handleChange}>
                              <LinkTab label="Busqueda" href="page1" />
                              <LinkTab label="Alarmas" href="page2" />
                              <LinkTab label="Listas" href="page3" />
                              <LinkTab label="Listas de eventos" href="page3" />
                          </Tabs>
                      </AppBar>
                      {value === 0 && <TabContainer>{modalEventSearchBox}</TabContainer>}
                      {value === 1 && <TabContainer>{modalEventsCluster}</TabContainer>}
                      {value === 2 && <TabContainer>Page Three</TabContainer>}
                  </div>
              </NoSsr>
          </Grid>
<Grid>

</Grid>

          </Grid>
    );

  }

}

Home.propTypes = {
    classes: PropTypes.object.isRequired,
};

export default withStyles(styles)(Home);

//export default Home;