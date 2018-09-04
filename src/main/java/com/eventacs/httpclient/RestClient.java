package com.eventacs.httpclient;

import com.google.api.client.http.*;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.JsonObjectParser;
import com.google.api.client.json.jackson2.JacksonFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class RestClient {

    private static final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();
    private static final JsonFactory JSON_FACTORY = new JacksonFactory();
    private static final HttpRequestFactory REQUEST_FACTORY = HTTP_TRANSPORT.createRequestFactory(request -> request.setParser(new JsonObjectParser(JSON_FACTORY)));

    public HttpResponse get(String url) {

        GenericUrl requestUrl = new GenericUrl(url);

        HttpResponse response = null;

        try {
            response =  REQUEST_FACTORY.buildGetRequest(requestUrl).execute();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // aca cerrar conexion
        }

        return response;

    }

}
