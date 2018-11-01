package com.eventacs.tests;

import com.eventacs.event.model.Category;
import com.eventacs.external.eventbrite.model.GetAccessToken;
import com.eventacs.external.telegram.client.httprequest.GetRequest;
import com.eventacs.external.telegram.client.httprequest.RequestLogin;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.util.List;

public class Test {
    RequestConfig requestConfig = RequestConfig.custom().setCookieSpec(CookieSpecs.BROWSER_COMPATIBILITY).setCookieSpec(ClientPNames.COOKIE_POLICY).build();
    CloseableHttpClient httpClient = HttpClients.custom().setDefaultRequestConfig(requestConfig).build();
    ObjectMapper objectMapper = new ObjectMapper();
    GetAccessToken response = null;

    @org.junit.Test
    public void givenNoToken_whenGetSecureRequest_thenUnauthorized() throws Exception {
        RequestLogin request = (new RequestLogin("usuario","clave"));
        GetAccessToken token = new GetAccessToken();
        response = this.objectMapper.readValue(httpClient.execute(request.build()).getEntity().getContent(), GetAccessToken.class);
        GetRequest requestApi =(new GetRequest("http://localhost:9000/eventacs/categories", response.getAccess_token()));
        List<Category> Categoryes = this.objectMapper.readValue(httpClient.execute(requestApi.build()).getEntity().getContent(), new TypeReference<List<Category>>(){});
    }
}
