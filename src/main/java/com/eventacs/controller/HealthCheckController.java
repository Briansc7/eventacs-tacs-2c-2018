package com.eventacs.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/eventacs")
public class HealthCheckController {

    @RequestMapping(value = "/health-check" , method = RequestMethod.GET)
    @ResponseBody
    public String getHealthState() {
        return "Status: Ok, App online.";
    }
}
