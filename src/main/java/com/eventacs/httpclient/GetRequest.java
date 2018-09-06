package com.eventacs.httpclient;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class GetRequest<T> extends Request<T> {

    private Class<T> type;

    public GetRequest(List<String> queryParams, List<String> pathVariables, String baseUrl, RestClient restClient, Optional<String> body) {
        super(queryParams, pathVariables, baseUrl, restClient, body);
    }

    @Override
    public T execute() {

        T res = null;

        try {
            res = super.getRestClient().get(this).parseAs(this.getType());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return res;

    }

    public Class<T> getType() {
        return type;
    }

}
