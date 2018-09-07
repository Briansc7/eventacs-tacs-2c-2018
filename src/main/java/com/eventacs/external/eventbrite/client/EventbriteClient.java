package com.eventacs.external.eventbrite.client;

import com.eventacs.httpclient.RestClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class EventbriteClient {

    @Autowired
    private RestClient restClient;

    private static final Logger LOGGER = LoggerFactory.getLogger(EventbriteClient.class);
    private static final String BASE_PATH = "https://www.eventbriteapi.com/v3";

    public EventbriteClient(RestClient restClient) {
        this.restClient = restClient;
    }

    public String getEvents(String keyWord, List<String> categories, LocalDate startDate, LocalDate endDate, Integer page) {

        String url = BASE_PATH + "/events/search?" + "q=" + keyWord + "&categories=" +
                     categories + "&start_date.range_start=" + startDate +
                     "&start_date.range_end=" + endDate;

        LOGGER.info("Retrieving events from: " + url);

        return restClient.getallPaginatedItems(url, page);

    }

}
