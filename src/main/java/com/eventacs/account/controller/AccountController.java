package com.eventacs.account.controller;

import com.eventacs.account.dto.UserLoginDTO;
import com.eventacs.account.service.AccountService;
import com.eventacs.account.dto.UserAccountDTO;
import com.eventacs.user.dto.UserInfoDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/eventacs")
public class AccountController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AccountController.class);

    @Autowired
    private AccountService accountService;

    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    @ResponseBody
    public UserInfoDTO signup(@RequestBody UserAccountDTO userAccountDTO) {
        LOGGER.info("/eventacs/signup [POST]");
        LOGGER.info("With user name:" + userAccountDTO.getName());

        return this.accountService.createUser(userAccountDTO);
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public UserInfoDTO login(@RequestBody UserLoginDTO userLoginDTO) {
        LOGGER.info("/eventacs/login [POST]");

        return this.accountService.login(userLoginDTO);
    }

    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    @ResponseBody
    public UserInfoDTO logout(@RequestBody String sessionCookieId) {
        LOGGER.info("/eventacs/logout [POST] With SessionCookie: {}", sessionCookieId);
        return this.accountService.logout(sessionCookieId);
    }

}
