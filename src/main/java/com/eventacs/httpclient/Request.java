package com.eventacs.httpclient;

import com.sun.javafx.fxml.builder.URLBuilder;
import org.springframework.core.GenericTypeResolver;
import org.springframework.util.TypeUtils;

import java.io.IOException;
import java.lang.reflect.TypeVariable;
import java.util.List;
import java.util.Optional;

abstract class Request<T> {

    private List<String> queryParams;
    private List<String> pathVariables;
    private String baseUrl;
    private RestClient restClient;
    private Optional<String> body;

    public Request(List<String> queryParams, List<String> pathVariables, String baseUrl, RestClient restClient, Optional<String> body) {
        this.queryParams = queryParams;
        this.pathVariables = pathVariables;
        this.baseUrl = baseUrl;
        this.restClient = restClient;
        this.body = body;
    }

    public abstract T execute() throws IOException;

    public String performUrl() {



    }

    public RestClient getRestClient() {
        return restClient;
    }

}
