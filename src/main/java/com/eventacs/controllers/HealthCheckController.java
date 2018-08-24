package com.eventacs.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/eventacs")
public class HealthCheckController {

    @RequestMapping(value = "/health-check" , method = RequestMethod.GET)
    public String getHealthState () {
        return "Status: Ok, App online.";
    }
}
