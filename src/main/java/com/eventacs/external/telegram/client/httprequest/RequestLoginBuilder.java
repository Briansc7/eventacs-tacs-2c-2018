package com.eventacs.external.telegram.client.httprequest;

import org.apache.http.client.methods.HttpUriRequest;
import java.util.Base64;

public class RequestLoginBuilder extends EventacsRequestBuilder {

    public RequestLoginBuilder(String username, String password) {
        super.postRequest("http://localhost:9001/oauth-server/oauth/token")
                .addHeader("Authorization", "Basic " +
                        Base64.getEncoder().
                                encodeToString(("eventacsClientId:secret").getBytes()))
                .addHeader("Content-Type", "application/x-www-form-urlencoded; charset=utf-8")
                .addParameter("grant_type", "password")
                .addParameter("password", password)
                .addParameter("username", username);
    }
}