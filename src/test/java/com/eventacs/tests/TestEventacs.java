package com.eventacs.tests;

import com.eventacs.event.model.Category;
import com.eventacs.external.eventbrite.model.GetAccessToken;
import com.eventacs.external.telegram.client.httprequest.EventacsCommands;
import com.eventacs.external.telegram.client.httprequest.EventacsRequestBuilder;
import com.eventacs.external.telegram.client.httprequest.RequestWitnToken;
import com.eventacs.external.telegram.client.httprequest.RequestLoginBuilder;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.*;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class TestEventacs {
//    RequestConfig requestConfig = RequestConfig.custom().setCookieSpec(CookieSpecs.BROWSER_COMPATIBILITY).setCookieSpec(ClientPNames.COOKIE_POLICY).build();
//    CloseableHttpClient httpClient = HttpClients.custom().setDefaultRequestConfig(requestConfig).build();
//    ObjectMapper objectMapper = new ObjectMapper();
    GetAccessToken response = null;

    @org.junit.Test
    public void givenNoToken_whenGetSecureRequest_thenUnauthorized() throws Exception {
//        response = objectMapper.readValue(httpClient.execute(
//                (new RequestLoginBuilder("usuario", "clave")).build())
//                .getEntity().getContent(), GetAccessToken.class);
        response = EventacsCommands.login("usuario", "clave");
        List<Category> Categoryes =  EventacsCommands.getCategories(response.getAccess_token());
//       List<Category> Categoryes = this.objectMapper.readValue(httpClient.execute((new RequestWitnToken("http://localhost:9000/eventacs/categories", response.getAccess_token())).build()).getEntity().getContent(), new TypeReference<List<Category>>() {});
    }


    @Test
    public void testCorrectMethods() throws Exception {
        class TestClass extends EventacsRequestBuilder {};
        Assert.assertTrue("No corresponde con el metodo DELETE",HttpDelete.METHOD_NAME.equalsIgnoreCase((new TestClass()).deleteRequest("").build().getMethod()));
        Assert.assertTrue("No corresponde con el metodo POST", HttpPost.METHOD_NAME.equalsIgnoreCase((new TestClass()).postRequest("").build().getMethod()));
        Assert.assertTrue("No corresponde con el metodo GET", HttpGet.METHOD_NAME.equalsIgnoreCase((new TestClass()).getRequest("").build().getMethod()));
        Assert.assertTrue("No corresponde con el metodo PUT",HttpPut.METHOD_NAME.equalsIgnoreCase((new TestClass()).putRequest("").build().getMethod()));
        Assert.assertTrue("No corresponde con el metodo PATCH", HttpPatch.METHOD_NAME.equalsIgnoreCase((new TestClass()).patchRequest("").build().getMethod()));
        Assert.assertTrue("No corresponde con el metodo OPTIONS",HttpOptions.METHOD_NAME.equalsIgnoreCase((new TestClass()).optionsRequest("").build().getMethod()));
    }
}