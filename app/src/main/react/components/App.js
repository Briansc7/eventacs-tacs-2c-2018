import React, { Component } from 'react';
import '../styles/App.css';
import '../styles/css/style.css';
import Home from './Home';
import { BrowserRouter as Router, Route, Switch } from 'react-router-dom';
import EventsTable from './EventsTable';
import EventListEdit from './EventListEdit';
import LoginForm from './LoginForm3';

class App extends Component {
    render() {
        return (
            <Router>
                <Switch>
                    <Route path='/' exact={true} component={LoginForm}/>
                    <Route path='/home' exact={true} component={Home}/>
                    <Route path='/events' exact={true} component={EventsTable}/>
                    <Route path='/event-lists/add/:id' component={EventListEdit}/>
                </Switch>
            </Router>
        )
    }
}

export default App;