package com.eventacs.external.telegram.client.httprequest;

import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;

public abstract class EventacsRequestBuilder {

    protected RequestBuilder requestBuilder;
//    public static HttpUriRequest requestLogin(String username, String password) {
//        return postRequest("http://localhost:9001/oauth-server/oauth/token")
//                .addHeader("Authorization", "Basic " +
//                        Base64.getEncoder().
//                                encodeToString(("eventacsClientId:secret").getBytes()))
//                .setEntity(EntityBuilder.create()
//                        .setParameters(
//                                new BasicNameValuePair("grant_type", "password"),
//                                new BasicNameValuePair("password", password),
//                                new BasicNameValuePair("username", username))
//                                .build())
//                .build();
//    }
    public EventacsRequestBuilder postRequest(String url) {
        requestBuilder = RequestBuilder.post(url);
        return this;
    }

    public EventacsRequestBuilder getRequest(String url) {
        requestBuilder = RequestBuilder.get(url);
        return this;
    }

    public EventacsRequestBuilder putRequest(String url) {
        requestBuilder = RequestBuilder.put(url);
        return this;
    }
    public EventacsRequestBuilder deleteRequest(String url) {
        requestBuilder = RequestBuilder.delete(url);
        return this;
    }
    public EventacsRequestBuilder patchRequest(String url) {
        requestBuilder = RequestBuilder.patch(url);
        return this;
    }
    public EventacsRequestBuilder optionsRequest(String url) {
        requestBuilder = RequestBuilder.options(url);
        return this;
    }

    public EventacsRequestBuilder addHeader(String key, String value) {
        requestBuilder.setHeader(new BasicHeader(key, value));
        return this;
    }

    public EventacsRequestBuilder addParameter(String key, String value) {
        requestBuilder.addParameters(new BasicNameValuePair(key, value));
        return this;
    }

    public EventacsRequestBuilder addAccessToken(String tokenAccess, String tokenType) {
        this.addHeader("Authorization", tokenType + " " + tokenAccess);
        return this;
    }

    public HttpUriRequest build() {
        return requestBuilder.build();
    }
}