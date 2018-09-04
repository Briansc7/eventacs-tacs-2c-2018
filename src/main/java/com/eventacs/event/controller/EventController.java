package com.eventacs.event.controller;

import com.eventacs.event.model.CreateEventDTO;
import com.eventacs.event.model.Timelapse;
import com.eventacs.event.service.EventService;
import com.eventacs.event.model.Event;
import com.eventacs.user.dto.UserInfoDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@Controller
@RequestMapping("/eventacs")
public class EventController {

    private static final Logger LOGGER = LoggerFactory.getLogger(EventController.class);

    @Autowired
    private EventService eventService;

    @RequestMapping(value = "/events", method = RequestMethod.GET)
    @ResponseBody
    public List<Event> getEvents(@RequestParam("criteria") List<String> criterias) {
        return eventService.getEvents(criterias);
    }

    @RequestMapping(value = "/event-lists", method = RequestMethod.POST)
    @ResponseBody
    public String createEventList(@RequestBody CreateEventDTO createEventDTO){
        LOGGER.info("/eventacs/event-lists [POST]");
        LOGGER.info("User id: {}, listName : {}", createEventDTO.getUserId(), createEventDTO.getListName());

        return eventService.createEventList(createEventDTO.getUserId(), createEventDTO.getListName());
    }


    @RequestMapping(value = "/event-lists/{listId}/{eventId}", method = RequestMethod.PUT)
    @ResponseBody
    public void addEvent(@PathVariable String listId, @PathVariable String eventId) {
        LOGGER.info("/eventacs/event-lists/{}/{} [PUT]", listId, eventId);

        eventService.addEvent(listId, eventId);
    }

    @RequestMapping(value = "/event-lists/{listId}", method = RequestMethod.PUT)
    @ResponseBody
    public String changeListName(@PathVariable String listId, @RequestBody String listName) {
        LOGGER.info("/eventacs/event-lists/{} with listName: {} [PUT]", listId, listName);

        return eventService.changeListName(listId, listName);
    }

    @RequestMapping(value = "/event-lists/{listId}", method = RequestMethod.DELETE)
    @ResponseBody
    public String deleteEventList(@PathVariable String listId) {
        LOGGER.info("/eventacs/event-lists/{} [DELETE]", listId);

        return eventService.deleteEventList(listId);
    }


    @RequestMapping(value = "/events/count", method = RequestMethod.GET)
    @ResponseBody
    public BigDecimal count(@RequestParam("timelapse") Timelapse timelapse) {
        LOGGER.info("/eventacs/events/count [GET] Timelapse: {}", timelapse.getValue());
        return this.eventService.count(timelapse);
    }

    @RequestMapping(value = "/events/{eventId}/watchers", method = RequestMethod.GET)
    @ResponseBody
    public List<UserInfoDTO> getWatchers(@PathVariable String eventId) {
        LOGGER.info("/eventacs/{}/watchers [GET]", eventId);
        return this.eventService.getWatchers(eventId);
    }

}
