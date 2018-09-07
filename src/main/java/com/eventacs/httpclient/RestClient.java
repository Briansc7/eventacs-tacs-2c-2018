package com.eventacs.httpclient;

import com.google.api.client.http.*;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.JsonObjectParser;
import com.google.api.client.json.jackson2.JacksonFactory;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Component
public class RestClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(RestClient.class);
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

    public String getAsJson(String url) {

        try {
            HttpClient client = HttpClientBuilder.create().build();
            HttpGet request = new HttpGet(url);

            request.addHeader("Authorization", "Bearer AA5PBW6TGCUULJPPZURJ"); //TODO SACAR A VARIABLES TIPO Q SE MANDE X CONF

            org.apache.http.HttpResponse response = client.execute(request);

            LOGGER.info("Response Code : " + response.getStatusLine().getStatusCode());

            BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

            return rd.readLine();

        } catch (IOException e) {
            LOGGER.error("Error al conectarse", e);
            throw new IllegalArgumentException(e);
        }
    }


    public String getallPaginatedItems(String url, Integer page) {
        return getAsJson(url + "&page=" + page);
    }
}