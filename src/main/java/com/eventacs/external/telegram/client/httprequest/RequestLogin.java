package com.eventacs.external.telegram.client.httprequest;

import org.apache.http.client.methods.HttpPost;
import java.util.Base64;

public class RequestLogin extends PostRequest {

    public RequestLogin(String username, String password) {
        super("http://localhost:9001/oauth-server/oauth/token");
        addBodyData("grant_type", "password");
        addBodyData("password", password);
        addBodyData("username", username);
        addHeader("Authorization", "Basic " +
                Base64.getEncoder().
                        encodeToString(("eventacsClientId:secret").getBytes()));
    }
}