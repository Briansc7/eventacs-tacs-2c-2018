package com.eventacs.user.controller;

import com.eventacs.user.dto.AlarmDTO;
import com.eventacs.user.dto.SearchDTO;
import com.eventacs.user.dto.UserDataDTO;
import com.eventacs.user.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.QueryParam;
import java.math.BigDecimal;
import java.util.List;

@Controller
@RequestMapping("/eventacs")
public class UserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/users/{userId}", method = RequestMethod.GET)
    @ResponseBody
    public UserDataDTO getUserData(@PathVariable String userId) {
        LOGGER.info("/users/{} [GET]", userId);
        return this.userService.getUser(userId);
    }

//    @RequestMapping(value = "/users", method = RequestMethod.GET)
//    @ResponseBody
//    public List<UserInfoDTO> getUsers() {
//        LOGGER.info("/users [GET]");
//        return this.userService.getUsers();
//    }

    @RequestMapping(value = "/users/{username}/alarms", method = RequestMethod.POST)
    @ResponseBody
    public AlarmDTO createAlarm(@PathVariable String username, @RequestBody (required = false) SearchDTO searchDTO) {
        LOGGER.info("/users/alarms [POST] {} for {}", searchDTO, username);
        return this.userService.createAlarm(searchDTO, username);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/users/{username}/alarms/count", method = RequestMethod.GET)
    @ResponseBody
    public BigDecimal countAlarms(@PathVariable String username) {
        LOGGER.info("/users/{}/alarms/count [GET]", username);
        return this.userService.countAlarms(username);
    }

}
