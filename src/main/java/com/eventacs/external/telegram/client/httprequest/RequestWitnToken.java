package com.eventacs.external.telegram.client.httprequest;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

public class RequestWitnToken extends EventacsRequestBuilder {
    public RequestWitnToken(String method, String url, String accessToken) {
        try {
            Method requestMethod = this.getClass().getMethod(method, String.class);
            requestMethod.invoke(this, url);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
//        getRequest(url);
        addAccessToken( accessToken, "Bearer ");
        addHeader("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");
    }

    public RequestWitnToken(String method, String url, String accessToken, Map<String,String> parameters) {
        this(method, url, accessToken);
        parameters.forEach( (k,v) -> addParameter(k,v));
    }
}