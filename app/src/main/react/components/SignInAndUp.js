import React from 'react';
import PropTypes from 'prop-types';
import Avatar from '@material-ui/core/Avatar';
import Button from '@material-ui/core/Button';
import CssBaseline from '@material-ui/core/CssBaseline';
import FormControl from '@material-ui/core/FormControl';
import Input from '@material-ui/core/Input';
import InputLabel from '@material-ui/core/InputLabel';
import LockIcon from '@material-ui/icons/LockOutlined';
import Paper from '@material-ui/core/Paper';
import Typography from '@material-ui/core/Typography';
import Tabs from '@material-ui/core/Tabs';
import Tab from '@material-ui/core/Tab';
import Grid from '@material-ui/core/Grid';
import withStyles from '@material-ui/core/styles/withStyles';



const styles = theme => ({
    main: {
        width: 'auto',
        display: 'block', // Fix IE 11 issue.
        marginLeft: theme.spacing.unit * 3,
        marginRight: theme.spacing.unit * 3,
        [theme.breakpoints.up(400 + theme.spacing.unit * 3 * 2)]: {
            width: 400,
            marginLeft: 'auto',
            marginRight: 'auto',
        },
    },
    paper: {
        marginTop: theme.spacing.unit * 4, //*8
        display: 'flex',
        flexDirection: 'column',
        alignItems: 'center',
        padding: `${theme.spacing.unit * 2}px ${theme.spacing.unit * 3}px ${theme.spacing.unit * 0.5}px`,//*0.5 --> 3
    },
    avatar: {
        margin: theme.spacing.unit,
        backgroundColor: theme.palette.secondary.main,
    },
    form: {
        width: '100%', // Fix IE 11 issue.
        marginTop: theme.spacing.unit,
    },
    submit: {
        marginTop: theme.spacing.unit * 3,
    },
    root: {
        flexGrow: 1,
        width: '100%',
        backgroundColor: theme.palette.background.paper,
    },
    grid: {
        alignItems: 'center',
    }
});

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

//function SignIn(props)
class SignIn extends React.Component {
    constructor(props) {
        super(props);
        this.validateUserNameSignIn = this.validateUserNameSignIn.bind(this);
        this.validatePasswordSignIn = this.validatePasswordSignIn.bind(this);
        this.validateUserNameSignUp = this.validateUserNameSignUp.bind(this);
        this.validateFullNameSignUp = this.validateFullNameSignUp.bind(this);
        this.validateEmailSignUp = this.validateEmailSignUp.bind(this);
        this.validatePasswordSignUp = this.validatePasswordSignUp.bind(this);
        this.validateRePasswordSignUp = this.validateRePasswordSignUp.bind(this);
        this.state = {
            isValidUserNameSignIn: false,
            isValidUserNameSignUp: false,
            isValidEmailSignUp: false,
            isValidRePasswordSignUp: false,
            isValidPasswordSignUp: false,
            isValidPasswordSignIn: false,
            isValidFullNameSignUp: false,
            valueSignInButton: true,
            valueSignUpButton: true,
            userNameSignInNotEmpty: false,
            passwordSignInNotEmpty: false,
            userNameSignUpNotEmpty: false,
            fullNameSignUpNotEmpty: false,
            emailSignUpNotEmpty: false,
            passwordSignUpNotEmpty: false,
            rePasswordSignUpNotEmpty: false,
            value: 0,
        };
    };

    clearForms = () => {
        this.setState({isValidUserNameSignIn: false,
            isValidUserNameSignUp: false,
            isValidEmailSignUp: false,
            isValidRePasswordSignUp: false,
            isValidPasswordSignUp: false,
            isValidPasswordSignIn: false,
            isValidFullNameSignUp: false,
            valueSignInButton: true,
            valueSignUpButton: true,
            userNameSignInNotEmpty: false,
            passwordSignInNotEmpty: false,
            userNameSignUpNotEmpty: false,
            fullNameSignUpNotEmpty: false,
            emailSignUpNotEmpty: false,
            passwordSignUpNotEmpty: false,
            rePasswordSignUpNotEmpty: false,
                        })
    }

    validateUserNameSignIn = (e) => {
        const validateUserNameRegEx = /^[a-zA-Z0-9](_(?!(\.|_))|\.(?!(_|\.))|[a-zA-Z0-9]){6,18}[a-zA-Z0-9]$/;
        (!validateUserNameRegEx.test(e.target.value)) ? this.setState({isValidUserNameSignIn: true}) : this.setState({isValidUserNameSignIn: false});
        if(e.target.value.length > 0) {this.setState({userNameSignInNotEmpty: true})};
    };

    validatePasswordSignIn = (e) => {
        const validatePasswordRegEx = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,10}$/;
        //Minimum eight and maximum 10 characters, at least one uppercase letter, one lowercase letter, one number and one special character
        (!validatePasswordRegEx.test(e.target.value)) ? this.setState({isValidPasswordSignIn: true}) : this.setState({isValidPasswordSignIn: false});
        if(e.target.value.length > 0) {this.setState({passwordSignInNotEmpty: true})};
    };

    validateUserNameSignUp = (e) => {
        const validateUserNameRegEx = /^[a-zA-Z0-9](_(?!(\.|_))|\.(?!(_|\.))|[a-zA-Z0-9]){6,18}[a-zA-Z0-9]$/;
        (!validateUserNameRegEx.test(e.target.value)) ? this.setState({isValidUserNameSignUp: true}) : this.setState({isValidUserNameSignUp: false});
        if(e.target.value.length > 0) {this.setState({userNameSignUpNotEmpty: true})};
    };

    validateFullNameSignUp = (e) => {
        const validateFullNameRegEx = /^([A-Za-z'\u00C0-\u00D6\u00D8-\u00f6\u00f8-\u00ff\s]*)$/;
        (!validateFullNameRegEx.test(e.target.value)) ? this.setState({isValidFullNameSignUp: true}) : this.setState({isValidFullNameSignUp: false});
        if(e.target.value.length > 0) {this.setState({fullNameSignUpNotEmpty: true})};
    };

    validateEmailSignUp = (e) => {
        const validateEmailRegEx = /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
        (!validateEmailRegEx.test(e.target.value)) ? this.setState({isValidEmailSignUp: true}) : this.setState({isValidEmailSignUp: false});
        if(e.target.value.length > 0) {this.setState({emailSignUpNotEmpty: true})};
    };

    validatePasswordSignUp = (e) => {
        const validatePasswordRegEx = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,10}$/;
        //Minimum eight and maximum 10 characters, at least one uppercase letter, one lowercase letter, one number and one special character
        (!validatePasswordRegEx.test(e.target.value)) ? this.setState({isValidPasswordSignUp: true}) : this.setState({isValidPasswordSignUp: false});
        if(e.target.value.length > 0) {this.setState({passwordSignUpNotEmpty: true})};
    };

    validateRePasswordSignUp = (e) => {
        const validatePasswordRegEx = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,10}$/;
        //Minimum eight and maximum 10 characters, at least one uppercase letter, one lowercase letter, one number and one special character
        (!validatePasswordRegEx.test(e.target.value)) ? this.setState({isValidRePasswordSignUp: true}) : this.setState({isValidRePasswordSignUp: false});
        if(e.target.value.length > 0) {this.setState({rePasswordSignUpNotEmpty: true})};
    };

    handleChange = (event, value) => {
        this.clearForms();
        this.setState({ value });
    };

    canBeSubmittedSignIn() {
        const {isValidUserNameSignIn, isValidPasswordSignIn, passwordSignInNotEmpty, userNameSignInNotEmpty} = this.state;
        return (((isValidUserNameSignIn || isValidPasswordSignIn) === false) && ((passwordSignInNotEmpty && userNameSignInNotEmpty) === true));
    }

    canBeSubmittedSignUp() {
        const { isValidUserNameSignUp, isValidEmailSignUp, isValidRePasswordSignUp, isValidPasswordSignUp,
            isValidFullNameSignUp, userNameSignUpNotEmpty, fullNameSignUpNotEmpty, emailSignUpNotEmpty,
            passwordSignUpNotEmpty, rePasswordSignUpNotEmpty} = this.state;
        return (((isValidUserNameSignUp || isValidEmailSignUp || isValidRePasswordSignUp || isValidPasswordSignUp
            || isValidFullNameSignUp) === false) && ((userNameSignUpNotEmpty && fullNameSignUpNotEmpty &&
            emailSignUpNotEmpty && passwordSignUpNotEmpty && rePasswordSignUpNotEmpty) === true));
    }

    render() {
        const isEnabledSignIn = this.canBeSubmittedSignIn();
        const isEnabledSignUp = this.canBeSubmittedSignUp();
        const { classes } = this.props;
        const { value } = this.state;

        return (
            <main className={classes.main}>
                <CssBaseline />
                <Paper className={classes.paper}>
                    <Tabs
                        value={this.state.value}
                        onChange={this.handleChange}
                        indicatorColor="primary"
                        textColor="primary"
                        centered
                    >
                        <Tab label="SignIn" />
                        <Tab label="SignUp" />
                    </Tabs>
                    {value === 0 && <TabContainer>
                        <Grid container>
                            <Grid item xs={3}>
                                <Avatar className={classes.avatar}>
                                    <LockIcon/>
                                </Avatar>
                            </Grid>
                            <Grid item xs={6} className={classes.grid}>
                                <Typography component="h1" variant="h4">Eventacs
                                </Typography>
                            </Grid>
                        </Grid>
                        <form className={classes.form} onSubmit={(e) => {this.validateFormSignIn(e)}}>
                            <FormControl error={this.state.isValidUserNameSignIn} margin="normal" fullWidth>
                                <InputLabel htmlFor="text">Username</InputLabel>
                                <Input onChange={(e) => {this.validateUserNameSignIn(e)}} id="text" name="usernameSignIn" autoComplete="text" autoFocus />
                            </FormControl>
                            <FormControl error={this.state.isValidPasswordSignIn} margin="normal" fullWidth>
                                <InputLabel htmlFor="password">Password</InputLabel>
                                <Input onChange={(e) => {this.validatePasswordSignIn(e)}} name="password" type="password" id="password" autoComplete="current-password" />
                            </FormControl>
                            <Button
                                disabled={!isEnabledSignIn}
                                type="submit"
                                fullWidth
                                variant="contained"
                                color="primary"
                                className={classes.submit}
                            >
                                Sign in
                            </Button>
                        </form>
                    </TabContainer>}
                    {value === 1 && <TabContainer><Grid container>
                        <Grid item xs={3}>

                            <Avatar className={classes.avatar}>
                                <LockIcon/>
                            </Avatar>
                        </Grid>
                        <Grid item xs={6} className={classes.grid}>
                            <Typography component="h1" variant="h4">Eventacs
                            </Typography>
                        </Grid>
                    </Grid>
                        <form className={classes.form} onSubmit={(e) => {this.validateFormSignIn(e)}}>
                            <FormControl error={this.state.isValidUserNameSignUp} margin="normal" fullWidth>
                                <InputLabel htmlFor="text">Username</InputLabel>
                                <Input id="text" name="text" onChange={(e) => {this.validateUserNameSignUp(e)}} autoComplete="text" autoFocus />
                            </FormControl>
                            <FormControl error={this.state.isValidFullNameSignUp} margin="normal" fullWidth>
                                <InputLabel htmlFor="text">Full Name</InputLabel>
                                <Input id="text" name="text" onChange={(e) => {this.validateFullNameSignUp(e)}} autoComplete="text" />
                            </FormControl>
                            <FormControl error={this.state.isValidEmailSignUp} margin="normal" fullWidth>
                                <InputLabel htmlFor="text">Email</InputLabel>
                                <Input id="email" name="text" onChange={(e) => {this.validateEmailSignUp(e)}}  autoComplete="text" />
                            </FormControl>
                            <FormControl error={this.state.isValidPasswordSignUp} margin="normal" fullWidth>
                                <InputLabel htmlFor="password">Password</InputLabel>
                                <Input name="password" type="password" onChange={(e) => {this.validatePasswordSignUp(e)}} id="password" autoComplete="new-password" />
                            </FormControl>
                            <FormControl error={this.state.isValidRePasswordSignUp} margin="normal" fullWidth>
                                <InputLabel htmlFor="rePassword">Retype Password</InputLabel>
                                <Input name="rePassword" type="password" onChange={(e) => {this.validateRePasswordSignUp(e)}} id="rePassword" autoComplete="new-password" />
                            </FormControl>
                            <Button
                                disabled={!isEnabledSignUp}
                                type="submit"
                                fullWidth
                                variant="contained"
                                color="primary"
                                className={classes.submit}
                            >
                                Sign in
                            </Button>
                        </form></TabContainer>}
                </Paper>
            </main>
        );}
}

SignIn.propTypes = {
    classes: PropTypes.object.isRequired,
};

export default withStyles(styles)(SignIn);
