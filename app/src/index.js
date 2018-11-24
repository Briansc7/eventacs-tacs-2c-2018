import React from 'react';
import ReactDOM from 'react-dom';
// import './main/react/styles/index.css';
//import './main/react/components/Fancy-Form-Example-master/src/index.css';
//import App from './main/react/components/Fancy-Form-Example-master/src/App';
import App from './main/react/components/SignInAndUp';
import * as serviceWorker from './main/react/serviceWorker';
//import 'bootstrap/dist/css/bootstrap.min.css';

ReactDOM.render(<App />, document.getElementById('root'));
serviceWorker.unregister();