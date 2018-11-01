package com.eventacs.external.telegram.client.httprequest;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

public class GetRequest extends HttpGet {
//    HttpGet request;
    Map<String, String> headersData = new HashMap<String, String>();
    Map<String, String> parametersData = new HashMap<String, String>();

    public GetRequest(String url, String accessToken) {
        super(url);
        addHeader("Authorization", "Bearer " + accessToken);
        addHeader("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");
    }

    public GetRequest buildHeader() {
        headersData.forEach((k, v) -> this.setHeader(k,v));
        return this;
    }

    public GetRequest buildParameters() throws URISyntaxException {
        URIBuilder uriBuilder = new URIBuilder(this.getURI());
        parametersData.forEach((k, v) -> uriBuilder.addParameter(k,v));
        this.setURI(uriBuilder.build());
        return this;
    }

    public void addHeader(String key, String value) {
        headersData.put(key,value);
    }

    public void addParametersData(String key, String value) {
        parametersData.put(key,value);
    }

    public GetRequest build() throws URISyntaxException {
        return this.buildHeader().buildParameters();
    }
}