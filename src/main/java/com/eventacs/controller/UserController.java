package com.eventacs.controller;

import com.eventacs.dto.UserDTO;
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

    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    @ResponseBody
    public String signUpUser(@RequestBody UserDTO userDTO) {

        LOGGER.info("/book [POST]");

        return "User Creado!";
    }
}
