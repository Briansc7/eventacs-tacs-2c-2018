package com.eventacs.external.telegram.client.httprequest;

public class RequestPostWithToken extends PostRequest {

    public RequestPostWithToken(String url, String accessToken) {
        super(url);
        addHeader("Authorization", "Bearer " + accessToken);
    }
}