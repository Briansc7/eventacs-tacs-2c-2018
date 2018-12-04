import React from 'react';
import ReactDOM from 'react-dom';
// import './main/react/styles/index.css';
import App from './main/react/components/App';
import * as serviceWorker from './main/react/serviceWorker';
//import 'bootstrap/dist/css/bootstrap.min.css';

ReactDOM.render(<App />, document.getElementById('root'));
serviceWorker.unregister();