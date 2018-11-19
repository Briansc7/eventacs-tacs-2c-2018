import React, { Component } from 'react';
import '../styles/css/style.css';

export default class LoginForm extends Component {

    constructor() {
        super();
        this.state = {username: "", password: ""};
        this.handleLoginData = this.handleLoginData.bind(this);
        this.updateUsername = this.updateUsername.bind(this);
        this.updatePassword = this.updatePassword.bind(this);
    }

    updateUsername = event => {this.setState({username: event.target.value})};
    updatePassword = event => {this.setState({password: event.target.value})};

    handleLoginData = () => {this.props.loginHandler(this.state.username, this.state.password)};

    render() {
        return (
            <section>
                <div className="layer"></div>
                <div className="container">
                    {
                        true ?
                            <div className="login-form">
                            <h1>Sign In</h1>
                            <form>
                                <input type="text" name="username" placeholder="username"/>
                                <input type="password" name="password" placeholder="password"/>
                                <input type="submit" name="" value="Login"/>
                                <input type="button" name="boton" placeholder="signup"/>
                            </form>
                        </div> :
                            <div className="login-form">
                            <h1>Sign Up</h1>
                            <form>
                                <input type="text" name="username" placeholder="username"/>
                                <input type="password" name="password" placeholder="password"/>
                                <input type="password" name="retryPassword" placeholder="password"/>
                                <input type="email" name="email" placeholder="email"/>
                                <input type="submit" name="" value="SingUp"/>
                                <input type="button" name="boton" placeholder="signin"/>
                            </form>
                        </div>
                    }
                </div>
            </section>
        );
    }
}