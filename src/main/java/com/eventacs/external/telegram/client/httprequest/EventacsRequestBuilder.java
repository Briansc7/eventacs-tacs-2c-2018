package com.eventacs.external.telegram.client.httprequest;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;

public abstract class EventacsRequestBuilder {

    protected RequestBuilder requestBuilder;

    public EventacsRequestBuilder postRequest(String url) {
        requestBuilder = RequestBuilder.post(url)
                .addHeader("Content-Type", "application/json");
        return this;
    }

    public EventacsRequestBuilder getRequest(String url) {
        requestBuilder = RequestBuilder.get(url)
                .addHeader("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");
        return this;
    }

    public EventacsRequestBuilder putRequest(String url) {
        requestBuilder = RequestBuilder.put(url)
                .addHeader("Content-Type", "application/json");;
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

    public EventacsRequestBuilder addEntity(HttpEntity entity) {
        requestBuilder.setEntity(entity);
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