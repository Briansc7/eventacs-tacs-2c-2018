package com.eventacs.external.telegram.client.httprequest;

import com.eventacs.event.model.Category;
import com.eventacs.event.model.EventList;
import com.eventacs.event.model.EventsResponse;
import com.eventacs.external.eventbrite.model.GetAccessToken;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import java.io.IOException;
import java.math.BigInteger;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class EventacsCommands {
    private static ObjectMapper objectMapper = new ObjectMapper();
    private static RequestConfig requestConfig = RequestConfig.custom().setCookieSpec(CookieSpecs.BROWSER_COMPATIBILITY).setCookieSpec(ClientPNames.COOKIE_POLICY).build();
    private static CloseableHttpClient httpClient = HttpClients.custom().setSSLContext((new SecureSSL()).getSSLContext()).setDefaultRequestConfig(requestConfig).build();

    public static GetAccessToken login(String username, String password) {
        GetAccessToken response = null;
    try {
        response = objectMapper.readValue(httpClient.execute(
                (new RequestLoginBuilder(username, password)).build())
                .getEntity().getContent(), GetAccessToken.class);
    } catch (IOException e) {
        e.printStackTrace();
    }
        return response;
    }

    public static List<Category> getCategories(String accessToken) {
        try {
            return objectMapper.readValue(httpClient.execute(
                    (new RequestWitnToken("getRequest","https://eventacs.com:9000/eventacs/categories", accessToken))
                            .build()).getEntity().getContent(), new TypeReference<List<Category>>() {});
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void addEventToList(String accessToken, String listID, String eventID, String userID) {
        try {
            String jsonString = "{\"userId\":\""+userID+"\"}";
            httpClient.execute(
                    (new RequestWitnToken("putRequest","https://eventacs.com:9000/eventacs/event-lists/"+listID+"/"+eventID, accessToken)).addEntity(new ByteArrayEntity(jsonString.getBytes("UTF-8")))
                            .build()).getEntity().getContent();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static EventsResponse getEvents(String accessToken, Optional<String> keyword, Optional<List<String>> categories, Optional<LocalDate> startDate, Optional<LocalDate> endDate, Optional<BigInteger> page) {
        EventsResponse eventsResponse = null;
        try {
            RequestWitnToken request = (new RequestWitnToken("getRequest","https://eventacs.com:9000/eventacs/events", accessToken));
            addParameterIfPresent("keyword", keyword, request);
            addParameterIfPresent("startDate", startDate, request);
            addParameterIfPresent("endDate", endDate, request);
            addParameterIfPresent("page", page, request);
            addParameterIfPresent("categories", categories, request);
            eventsResponse = objectMapper.readValue(httpClient.execute(
                    request.build()).getEntity().getContent(), EventsResponse.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return eventsResponse;
    }

    public static EventList getEventList(String accessToken, String listId) {
        EventList eventList = null;
        try {
            eventList = objectMapper.readValue(httpClient.execute(
                    (new RequestWitnToken("getRequest", "https://eventacs.com:9000/eventacs/event-lists/" + listId, accessToken)).build()).getEntity().getContent(), EventList.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return eventList;
    }

    private static void addParameterIfPresent(String parameterName, Object parameter, RequestWitnToken request) {
        if(((Optional<Object>)parameter).isPresent()) {
            String parameters;
            if (((Optional<Object>) parameter).get() instanceof List<?> ) {
                parameters = String.join(",", getStringParameterList((Optional<Object>) parameter));
            } else {
                parameters = ((Optional<Object>)parameter).get().toString();
            }
            request.addParameter(parameterName, parameters);
        }
    }

    private static List<String> getStringParameterList(Optional<Object> parameter) {
        return ((List<?>) parameter.get()).stream().map(e -> (String) e).collect(Collectors.toList());
    }
}