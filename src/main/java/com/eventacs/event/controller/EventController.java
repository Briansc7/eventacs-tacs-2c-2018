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
        return this.eventService.getEvents(criterias);
    }

    @RequestMapping(value = "/event-lists", method = RequestMethod.POST)
    @ResponseBody
    public String createEventList(@RequestBody CreateEventDTO createEventDTO){
        LOGGER.info("/eventacs/event-lists [POST] With: userId: {} listName: {}", createEventDTO.getUserId(), createEventDTO.getListName());
        return this.eventService.createEventList(createEventDTO.getUserId(), createEventDTO.getListName());
    }

    @RequestMapping(value = "/event-lists/{listId}/{eventId}", method = RequestMethod.PUT)
    @ResponseBody
    public void addEvent(@PathVariable String listId, @PathVariable String eventId) {
        LOGGER.info("/eventacs/event-lists/{}/{} [PUT]", listId, eventId);
        this.eventService.addEvent(listId, eventId);
    }

    @RequestMapping(value = "/event-lists/{listId}", method = RequestMethod.PUT)
    @ResponseBody
    public String changeListName(@PathVariable String listId, @RequestBody String listName) {
        LOGGER.info("/eventacs/event-lists/{} [PUT] With listName: {} ", listId, listName);
        return this.eventService.changeListName(listId, listName);
    }

    @RequestMapping(value = "/event-lists/{listId}", method = RequestMethod.DELETE)
    @ResponseBody
    public String deleteEventList(@PathVariable String listId) {
        LOGGER.info("/eventacs/event-lists/{} [DELETE]", listId);
        return this.eventService.deleteEventList(listId);
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

    @RequestMapping(value = "/event-lists/shared-events", method = RequestMethod.GET)
    @ResponseBody
    public List<Event> getSharedEvents(@RequestParam String listId, @RequestParam String anotherListId) {
        LOGGER.info("/eventacs/event-lists/shared-events [GET] Lists IDs: {}, {}", listId, anotherListId);
        return this.eventService.getSharedEvents(listId, anotherListId);
    }

}
