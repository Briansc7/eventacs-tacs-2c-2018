package com.eventacs.user.controller;

import com.eventacs.user.dto.AlarmDTO;
import com.eventacs.user.dto.SearchDTO;
import com.eventacs.user.dto.UserInfoDTO;
import com.eventacs.user.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
@RequestMapping("/eventacs")
public class UserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/users/{userId}", method = RequestMethod.GET)
    @ResponseBody
    public UserInfoDTO getUser(@PathVariable String userId) {
        LOGGER.info("/users/{} [GET]", userId);
        return this.userService.getUser(userId);
    }

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    @ResponseBody
    public List<UserInfoDTO> getUsers() {
        LOGGER.info("/users [GET]");
        return this.userService.getUsers();
    }

    @RequestMapping(value = "/users/{userId}/alarms", method = RequestMethod.POST)
    @ResponseBody
    public AlarmDTO createAlarm(@PathVariable String userId, @RequestBody SearchDTO searchDTO) {
        LOGGER.info("/users/{}/alarms [POST]", userId);
        return this.userService.createAlarm(userId, searchDTO);
    }

}
