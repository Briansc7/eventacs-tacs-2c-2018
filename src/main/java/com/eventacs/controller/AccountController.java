package com.eventacs.controller;

import com.eventacs.dto.UserDTO;
import com.eventacs.service.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/eventacs")
public class AccountController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AccountController.class);
    private AccountService accountService;

    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    @ResponseBody
    public String signup(@RequestBody UserDTO userDTO) {
        LOGGER.info("/eventacs/signup [POST] {1}", userDTO.getName());
        return "User Created!";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public String login(@RequestBody UserDTO userDTO) {
        LOGGER.info("/eventacs/login [POST]");
        return "User Login!";
    }

    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    @ResponseBody
    public String logout() {
        LOGGER.info("/eventacs/logout [POST]");
        return "User Logout!";
    }

}
