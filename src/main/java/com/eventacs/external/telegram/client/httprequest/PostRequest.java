package com.eventacs.external.telegram.client.httprequest;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class PostRequest extends HttpPost{

//    HttpPost request;
    Map<String, String> headersData = new HashMap<String, String>();
    List<NameValuePair> bodyData = new ArrayList<NameValuePair>();

    public PostRequest(String url) {
        super(url);
    }

    public PostRequest buildHeader() {
        headersData.forEach((k, v) -> this.setHeader(k,v));
        return this;
    }

    public PostRequest buildBody() throws UnsupportedEncodingException {
        this.setEntity(new UrlEncodedFormEntity(bodyData));
        return this;
    }

    public void addHeader(String key, String value) {
        headersData.put(key,value);
    }

    public void addBodyData(String key, String value) {
        bodyData.add(new BasicNameValuePair(key, value));
    }

    public PostRequest build() throws UnsupportedEncodingException {
        return this.buildHeader().buildBody();
    }
}