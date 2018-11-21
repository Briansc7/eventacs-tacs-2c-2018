import axios from 'axios';
// You can use any cookie library or whatever
// library to access your client storage.
//import cookie from 'cookie-machine';

axios.interceptors.request.use(function(config) {
    //const token = cookie.get(__TOKEN_KEY__);

    //if ( token != null ) {
        //config.headers.Authorization = `Bearer ${token}`;
        config.headers.Authorization = `Bearer 4eddcbf9-37a8-4dc6-b64a-dd07e4b36804`;
  //  }

    return config;
}, function(err) {
    return Promise.reject(err);
});