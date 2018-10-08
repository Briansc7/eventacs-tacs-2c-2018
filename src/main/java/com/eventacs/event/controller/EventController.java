package com.eventacs.event.controller;

import com.eventacs.event.dto.EventListCreationDTO;
import com.eventacs.event.dto.EventListDTO;
import com.eventacs.event.model.*;
import com.eventacs.event.service.EventService;
import com.eventacs.user.dto.UserInfoDTO;
import com.eventacs.user.model.UserId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/eventacs")
public class EventController {

    private static final Logger LOGGER = LoggerFactory.getLogger(EventController.class);

    @Autowired
    private EventService eventService;

    @RequestMapping(value = "/events", method = RequestMethod.GET)
    @ResponseBody
    public List<Event> getEvents(@RequestParam(name = "keyWord", required = false) Optional<String> keyWord,
                                 @RequestParam(name = "categories", required = false) Optional<List<String>> categories,
                                 @RequestParam(name = "startDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Optional<LocalDate> startDate,
                                 @RequestParam(name = "endDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Optional<LocalDate> endDate,
                                 @RequestParam(name = "page", required = false) Optional<BigInteger> page) {
        LOGGER.info("/eventacs/events [GET] With: keyWord: {} categories: {} startDate: {} endDate: {} page: {}", keyWord, categories, startDate, endDate, page);
        return this.eventService.getEvents(keyWord, categories, startDate, endDate, page);
    }

    @RequestMapping(value = "/categories", method = RequestMethod.GET)
    @ResponseBody
    public List<Category> getCategories() {
        LOGGER.info("/eventacs/categories [GET]");
        return this.eventService.getCategories();
    }

    @RequestMapping(value = "/event-lists/{listId}", method = RequestMethod.GET)
    @ResponseBody
    public EventList getEventList(@PathVariable String listId) {
        LOGGER.info("/eventacs/event-lists/{} [GET]", listId);
        return this.eventService.getEventList(listId);
    }

    @RequestMapping(value = "/event-lists", method = RequestMethod.POST)
    @ResponseBody
    public String createEventList(@RequestBody EventListCreationDTO eventListCreation){
        LOGGER.info("/eventacs/event-lists [POST] With: userId: {} listName: {}", eventListCreation.getUserId(), eventListCreation.getListName());
        return this.eventService.createEventList(eventListCreation);
    }

    @RequestMapping(value = "/event-lists/{listId}/{eventId}", method = RequestMethod.PUT)
    @ResponseBody
    public void addEvent(@PathVariable String listId, @PathVariable String eventId, @RequestBody UserId userId) {
        LOGGER.info("/eventacs/event-lists/{}/{} [PUT] for this userId: {}", listId, eventId, userId);
        this.eventService.addEvent(listId, eventId, userId.getUserId());
    }

    @RequestMapping(value = "/event-lists/{listId}", method = RequestMethod.PUT)
    @ResponseBody
    public String changeListName(@PathVariable String listId, @RequestBody ListName listName) {
        LOGGER.info("/eventacs/event-lists/{} [PUT] With listName: {} ", listId, listName);
        return this.eventService.changeListName(listId, listName.getListName());
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
        LOGGER.info("/eventacs/events/count [get] Timelapse: {}", timelapse.getValue());
        return this.eventService.count(timelapse);
    }

    @RequestMapping(value = "/events/{eventId}/watchers", method = RequestMethod.GET)
    @ResponseBody
    public List<UserInfoDTO> getWatchers(@PathVariable String eventId) {
        LOGGER.info("/eventacs/{}/watchers [get]", eventId);
        return this.eventService.getWatchers(eventId);
    }

    @RequestMapping(value = "/event-lists/shared-events", method = RequestMethod.GET)
    @ResponseBody
    public List<Event> getSharedEvents(@RequestParam String listId, @RequestParam String anotherListId) {
        LOGGER.info("/eventacs/event-lists/shared-events [get] Lists IDs: {}, {}", listId, anotherListId);
        return this.eventService.getSharedEvents(listId, anotherListId);
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String reactTest() {
        return "index";
    }

}
