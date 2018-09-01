package com.eventacs.controller;

import com.eventacs.service.Event;
import com.eventacs.service.EventService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/eventacs")
public class EventController {

    private static final Logger LOGGER = LoggerFactory.getLogger(EventController.class);

    private EventService service;

    @RequestMapping(value = "/events" , method = RequestMethod.GET)
    @ResponseBody
    public List<Event> getEvents(@RequestParam("criteria") List<String> criterias) {
        return service.getEvents(criterias);
    }

}
