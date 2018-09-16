package com.eventacs.httpclient;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;

@Component
public class RestClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(RestClient.class);

    private ObjectMapper objectMapper = new ObjectMapper();
    private RequestConfig requestConfig = RequestConfig.custom().setCookieSpec(CookieSpecs.BROWSER_COMPATIBILITY).setCookieSpec(ClientPNames.COOKIE_POLICY).build();
    private CloseableHttpClient httpClient = HttpClients.custom().setDefaultRequestConfig(requestConfig).build();

    public <T> T get(URI url, Class<T> clazz) {

        T response = null;

        try {
            HttpGet request = new HttpGet(url);
            request.setHeader("Authorization", "Bearer AA5PBW6TGCUULJPPZURJ");
            response = this.objectMapper.readValue(httpClient.execute(request).getEntity().getContent(), clazz);
            LOGGER.info("Perform [GET] URL: {} Headers: {}", url, request.getAllHeaders());
        } catch (JsonParseException e) {
            e.printStackTrace();
            LOGGER.error("Parse error", e);
        } catch (IOException e) {
            e.printStackTrace();
            LOGGER.error("Error connecting to client", e);
        }

        return response;

    }

}