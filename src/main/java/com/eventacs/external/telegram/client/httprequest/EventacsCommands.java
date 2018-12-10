package com.eventacs.external.telegram.client.httprequest;

import com.eventacs.event.dto.EventListCreationDTO;
import com.eventacs.event.model.*;
import com.eventacs.external.eventbrite.model.GetAccessToken;
import com.eventacs.user.dto.SearchDTO;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import java.io.IOException;
import java.math.BigInteger;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class EventacsCommands {
    private static ObjectMapper objectMapper = new ObjectMapper();
    private static RequestConfig requestConfig = RequestConfig.custom().setCookieSpec(CookieSpecs.BROWSER_COMPATIBILITY).setCookieSpec(ClientPNames.COOKIE_POLICY).build();
  //  con SSL private static CloseableHttpClient httpClient = HttpClients.custom().setSSLContext((new SecureSSL()).getSSLContext()).setDefaultRequestConfig(requestConfig).build();
    private static CloseableHttpClient httpClient = HttpClients.custom().setDefaultRequestConfig(requestConfig).build();

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
                    (new RequestWitnToken("getRequest","http://backend:9000/eventacs/categories", accessToken))
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
                    (new RequestWitnToken("putRequest","http://backend:9000/eventacs/event-lists/"+listID+"/"+eventID, accessToken)).addEntity(new ByteArrayEntity(jsonString.getBytes("UTF-8")))
                            .build()).getEntity().getContent();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void deleteEventList(String accessToken, String listID) {
        try {

            httpClient.execute(
                    (new RequestWitnToken("deleteRequest","http://backend:9000/eventacs/event-lists/"+listID, accessToken))
                            .build()).getEntity().getContent();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void createEventList(String accessToken, EventListCreationDTO eventListCreation) {
        try {
            String jsonString = "{\"listName\":\""+eventListCreation.getListName()+"\",\"userId\":\""+eventListCreation.getUserId()+"\"}";

            HttpUriRequest requestWitnToken = new RequestWitnToken("postRequest","http://backend:9000/eventacs/event-lists/", accessToken).addEntity(new ByteArrayEntity(jsonString.getBytes("UTF-8")))
                    .build();

            httpClient.execute(
                    requestWitnToken).getEntity().getContent();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void createAlarm(String accessToken, String username, SearchDTO searchDTO) {
        try {
/*
            ObjectMapper objectMapper = new ObjectMapper()
                    .registerModule(new ParameterNamesModule())
                    .registerModule(new Jdk8Module())
                    .registerModule(new JavaTimeModule());

            String jsonString = objectMapper.writeValueAsString(searchDTO);
*/

            String jsonString = "{\"keyword\":"
                    +(searchDTO.getKeyword().equals(Optional.empty())?null:"\""+searchDTO.getKeyword().get()+"\"")+
                    ",\"categories\":"
                    +(searchDTO.getCategories().equals(Optional.empty())?"[]":searchDTO.getCategories().get())+
                    ",\"startDate\":\""
                    +(searchDTO.getStartDate().equals(Optional.empty())?"":searchDTO.getStartDate().get())+
                    "\",\"endDate\":\""
                    +(searchDTO.getEndDate().equals(Optional.empty())?"":searchDTO.getEndDate().get())+
                            "\"}";

            HttpUriRequest requestWitnToken = new RequestWitnToken("postRequest","http://backend:9000/eventacs/users/"+username+"/alarms", accessToken).addEntity(new ByteArrayEntity(jsonString.getBytes("UTF-8")))
                    .build();

            httpClient.execute(
                    requestWitnToken).getEntity().getContent();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void changeListName(String accessToken, String listID, ListName listName){
        try {
            String jsonString = "{\"listName\":\""+listName.getListName()+"\"}";
            httpClient.execute(
                    (new RequestWitnToken("putRequest","http://backend:9000/eventacs/event-lists/"+listID, accessToken)).addEntity(new ByteArrayEntity(jsonString.getBytes("UTF-8")))
                            .build()).getEntity().getContent();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static EventsResponse getEvents(String accessToken, Optional<String> keyword, Optional<List<String>> categories, Optional<LocalDate> startDate, Optional<LocalDate> endDate, Optional<BigInteger> page) {
        EventsResponse eventsResponse = null;
        try {
            RequestWitnToken request = (new RequestWitnToken("getRequest","http://backend:9000/eventacs/events", accessToken));
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

    public static Event getEvent(String accessToken, String eventId){
        try {
            return objectMapper.readValue(httpClient.execute(
                    (new RequestWitnToken("getRequest","http://backend:9000/eventacs/events/" + eventId, accessToken))
                            .build()).getEntity().getContent(), Event.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static EventList getEventList(String accessToken, String listId) {
        EventList eventList = null;
        try {
            eventList = objectMapper.readValue(httpClient.execute(
                    (new RequestWitnToken("getRequest", "http://backend:9000/eventacs/event-lists/" + listId, accessToken)).build()).getEntity().getContent(), EventList.class);
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

    public static void logout(String accessToken){
        try {
            httpClient.execute(
                    (RequestBuilder.post("http://oauth-server:9001/oauth-server/oauth/token/revokeById/"+accessToken))
                            .build()).getEntity().getContent();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}