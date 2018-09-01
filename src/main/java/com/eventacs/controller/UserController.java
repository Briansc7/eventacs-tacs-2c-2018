package com.eventacs.controller;

import com.eventacs.dto.UserDTO;
import com.eventacs.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/eventacs")
public class UserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);
    private UserService userService;

    @RequestMapping(value = "/users/:userId/alarms", method = RequestMethod.POST)
    @ResponseBody
    public String createAlarm(@RequestBody UserDTO userDTO) {
        LOGGER.info("/users/{}/alarms [POST]");
        return "User Created!";
    }
}
