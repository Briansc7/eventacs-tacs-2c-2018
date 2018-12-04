import React, { Component } from 'react';
//import '../styles/css/style.css';
import {Row, Col, Button, Form, FormGroup, Label, Input, Nav, NavItem, NavLink, TabContent, TabPane} from 'reactstrap';
import classnames from 'classnames';
import "../styles/css/style4.css"

export default class LoginForm extends Component {


    constructor(props) {
        super(props);
        this.state = {
            activeTab: '1',
            userName: '',
            usernameValidOrInvalid: '',
            passwordValidOrInvalid: '',
            password: '',
            fullName: '',
            email: '',
            retypePassword: '',
            submitLogin: 'false',
            submitSignUp: 'false',
            tokenAccess: ''
        };
        this.toggle = this.toggle.bind(this);
        this.handlePassChange = this.handlePassChange.bind(this);
        this.handleUserChange = this.handleUserChange.bind(this);
        this.handleSubmitLogIn = this.handleSubmitLogIn.bind(this);
        this.dismissError = this.dismissError.bind(this);
    }

    dismissError() {
        this.setState({ error: '' });
    }

    handleSubmitLogIn(evt) {
        evt.preventDefault();

        return this.login();
    }

    handleUserChange(evt) {
        this.setState({
            userName: evt.target.value,
        });
    };

    handlePassChange(evt) {
        this.setState({
            password: evt.target.value,
        });
    }

    errors() {

    }
    login(){
        fetch('http://oauth-server:9001/oauth-server/oauth/token', {
            method: 'POST',
            headers: {
                'Authorization': 'Basic ZXZlbnRhY3NDbGllbnRJZDpzZWNyZXQ=',
                'content-Type': 'application/x-www-form-urlencoded; charset=utf-8'},
            body: encodeURIComponent('grant_type') + '=' + encodeURIComponent('password') + '&' +
                encodeURIComponent('password') + '=' + encodeURIComponent(this.state.password) + '&' +
                encodeURIComponent('username') + '=' + encodeURIComponent(this.state.userName)
        })
            .then(response => response.json())
            .then(data => this.setState({tokenAccess: data, isLoading: false}));
    }

    signUp(){
        fetch('http://backend:9000/eventacs/signup', {
            method: 'POST',
            headers: {
                'content-Type': 'application/json'},
            body: {
                'fullname':this.state.fullName,
                'email':this.state.email,
                'password':this.state.password,
                'username':this.state.userName
            },
        })
            .then(response => response.json())
            .then(data => this.setState({categories: data, isLoading: false}));
    }
    validateForm(){

    }

    toggle(tab) {
        if (this.state.activeTab !== tab) {
            this.setState({
                activeTab: tab
            });
        }
    }

    render() {
        return (
            <div className="container">
                <div className="frame">
                    <div className="nav">
                    <Nav tabs className="links">
                        <NavItem>
                            <NavLink
                                className={classnames({ active: this.state.activeTab === '1' })}
                                onClick={() => { this.toggle('1'); }}
                            >
                                Sign in
                            </NavLink>
                        </NavItem>
                        <NavItem>
                            <NavLink
                                className={classnames({ active: this.state.activeTab === '2' })}
                                onClick={() => { this.toggle('2'); }}
                            >
                                Sign up
                            </NavLink>
                        </NavItem>
                    </Nav>
                    </div>
                    <TabContent activeTab={this.state.activeTab}>
                        <TabPane tabId="1">
                            <Row>
                                <Col sm="6">
                                    <Form className="form-signin" onSubmit={this.handleSubmitLogIn}>
                                        <FormGroup>
                                            <Label for="username">Username</Label>
                                            <Input className="form-styling" type="text" name="username" id="username"
                                                   placeholder="" value={this.state.userName} onChange={this.handleUserChange}/>
                                        </FormGroup>
                                        <FormGroup>
                                            <Label for="password">Password</Label>
                                            <Input className="form-styling" type="password" name="password" id="password"
                                                   placeholder="" value={this.state.password} onChange={this.handlePassChange}/>
                                        </FormGroup>
                                        <Button className="btn-signin">LogIn</Button>{' '}
                                    </Form>
                                </Col>
                            </Row>
                        </TabPane>
                        <TabPane tabId="2">
                            <Row>
                                <Col sm="12">
                                    <Form className="form-signup" onSubmit={this.handleSubmit}>
                                    <FormGroup>
                                        <Label for="fullname">Full Name</Label>
                                        <Input className="form-styling" type="text" name="fullname" id="fullname" placeholder=""
                                               value={this.state.fullName} onChange={this.handleEmailChange}/>
                                    </FormGroup>
                                    <FormGroup>
                                        <Label for="email">Email</Label>
                                        <Input className="form-styling" type="email" name="email" id="email" placeholder=""
                                               value={this.state.email} onChange={this.handleEmailChange}/>
                                    </FormGroup>
                                    <FormGroup>
                                        <Label for="password">Password</Label>
                                        <Input className="form-styling" type="password" name="password" id="password" placeholder=""
                                               value={this.state.password} onChange={this.handlePassChange}/>
                                    </FormGroup>
                                    <FormGroup>
                                        <Label for="retypePassword">Retype Password</Label>
                                        <Input className="form-styling" type="password" name="retypePassword" id="retypePassword" placeholder=""
                                               value={this.state.retypePassword} onChange={this.handleRePassChange}/>
                                    </FormGroup>
                                    <Button className="btn-signup" onClick={this.signUp}>Sign Up</Button>
                                    </Form>
                                </Col>
                            </Row>
                        </TabPane>
                    </TabContent>
                </div>
            </div>
        );
    }
}