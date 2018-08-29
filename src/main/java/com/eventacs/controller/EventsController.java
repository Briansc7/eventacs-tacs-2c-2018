package com.eventacs.controller;

import com.eventacs.service.Event;
import com.eventacs.service.EventsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/eventacs")
public class EventsController {

    private EventsService service;

    @RequestMapping(value = "/events" , method = RequestMethod.GET)
    @ResponseBody
    public List<Event> getEvents(@RequestParam("criteria") List<String> criterias) {
        return service.getEvents(criterias);
    }

}
