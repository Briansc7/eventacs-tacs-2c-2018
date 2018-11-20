import React, { Component } from 'react';
//import '../styles/css/style.css';
import {Modal, ModalHeader, ModalFooter, ModalBody, Row, Col, Button, Form, FormGroup, Label, Input, Nav, NavItem, NavLink, TabContent, TabPane} from 'reactstrap';
import classnames from 'classnames';
import { Redirect } from 'react-router-dom'
//import "../styles/css/style4.css"

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
            tokenAccess: '',
            redirect: false,
            modal: false,
            modalTitle: '',
            modalBody: ''
        };
        this.toggle = this.toggle.bind(this);
        this.handlePassChange = this.handlePassChange.bind(this);
        this.handleRePassChange = this.handleRePassChange.bind(this);
        this.handleFullNameChange = this.handleFullNameChange.bind(this);
        this.handleUserChange = this.handleUserChange.bind(this);
        this.handleSubmitLogIn = this.handleSubmitLogIn.bind(this);
        this.handleSubmitSignUp = this.handleSubmitSignUp.bind(this);
        this.dismissError = this.dismissError.bind(this);
        this.openCloseModal = this.openCloseModal.bind(this);
    }

    dismissError() {
        this.setState({ error: '' });
    }

    handleSubmitLogIn(evt) {
        evt.preventDefault();

        return this.login();
    }

    handleSubmitSignUp(evt) {
        evt.preventDefault();

        return this.signUp();
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
    };

    handleRePassChange(evt) {
        this.setState({
            retypePassword: evt.target.value,
        });
    };

    handleFullNameChange(evt) {
        this.setState({
            fullName: evt.target.value,
        });
    };

    clearForm() {
        this.setState({
            activeTab: this.state.activeTab,
            userName: '',
            usernameValidOrInvalid: '',
            passwordValidOrInvalid: '',
            password: '',
            fullName: '',
            email: '',
            retypePassword: '',
            submitLogin: 'false',
            submitSignUp: 'false',
            redirect: false,
            tokenAccess: ''
        });
    }

    userCreated() {
        this.clearForm();
        this.setState({
            activeTab: 1,
            modalTitle: 'Usuario Creado',
            modalBody: 'El usuario fue creado.'
        });
        this.openCloseModal();
        console.log("USUARIO CREADO");
    }

    userNotCreated() {
        this.clearForm();
        this.setState({
            modalTitle: 'No se pudo crear el usuario',
            modalBody: 'Problema al crear el usuario, intente nuevamente.'
        });
        this.openCloseModal();
        console.log("USUARIO NO CREADO");
    }

    login(){
        fetch('https://eventacs.com:9001/oauth-server/oauth/token', {
            method: 'POST',
            headers: {
                'Authorization': 'Basic ZXZlbnRhY3NDbGllbnRJZDpzZWNyZXQ=',
                'content-Type': 'application/x-www-form-urlencoded; charset=utf-8'},
            body: encodeURIComponent('grant_type') + '=' + encodeURIComponent('password') + '&' +
                encodeURIComponent('password') + '=' + encodeURIComponent(this.state.password) + '&' +
                encodeURIComponent('username') + '=' + encodeURIComponent(this.state.userName)
        })
            .then(response => response.json())
            .then(data => {
                this.state.tokenAccess=data;
                console.log(data)});
        if(this.state.tokenAccess.access_token){
            this.setCookies(this.state.tokenAccess);
            localStorage.setItem("dataSession", JSON.stringify(this.state.tokenAccess));
            this.redirectToHome();
        } else {
            this.clearForm();
            this.notLogin();
        }
    }

    redirectToHome = () => {
        this.setState({
            redirect: true
        })
    }

    notLogin(){
        this.clearForm();
        this.setState({
            modalTitle: 'Login incorrecto',
            modalBody: 'Verifique usuario y contraseña'
        });
        this.openCloseModal();
        console.log("Login fallido");
    }

    setCookies(){

    }

    renderRedirect = () =>  {
        if (this.state.redirect) {
            return <Redirect to="/home"/>;
        }
    }

    signUp(){
        if(this.validateSignUp()){
            var payload = {fullName:this.state.fullName,password:this.state.password,userName:this.state.userName}
            console.log(payload)
            fetch('https://eventacs.com:9000/eventacs/signup', {
                method: 'POST',
                headers: {
                    'content-Type': 'application/json'},
                body: JSON.stringify( payload ),
            })
                .then(response => response.json())
                .then(response => {
                    response ? this.userCreated():this.userNotCreated();
                    });
        } else {
            console.log("No valido");
        }
    }

    validateSignUp(){
        return this.state.password == this.state.retypePassword;
    }

    toggle(tab) {
        if (this.state.activeTab !== tab) {
            this.setState({
                activeTab: tab
            });
        }
    }

    openCloseModal() {
        this.setState({
            modal: !this.state.modal
        });
    }

    render() {
        return (
            <div className="container">
                {this.renderRedirect()}
                <Modal isOpen={this.state.modal}>
                    <ModalHeader onClick={this.openCloseModal}>{this.state.modalTitle}</ModalHeader>
                    <ModalBody>
                        {this.state.modalBody}
                    </ModalBody>
                    <ModalFooter>
                        <Button color="primary" onClick={this.openCloseModal}>OK</Button>
                    </ModalFooter>
                </Modal>
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
                                    <Form className="form-signup" onSubmit={this.handleSubmitSignUp}>
                                    <FormGroup>
                                        <Label for="fullname">Full Name</Label>
                                        <Input className="form-styling" type="text" name="fullname" id="fullname" placeholder=""
                                               value={this.state.fullName} onChange={this.handleFullNameChange}/>
                                    </FormGroup>
                                    <FormGroup>
                                            <Label for="username">Username</Label>
                                            <Input className="form-styling" type="text" name="username" id="username"
                                                   placeholder="" value={this.state.userName} onChange={this.handleUserChange}/>
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
                                    <Button className="btn-signup">Sign Up</Button>{' '}
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