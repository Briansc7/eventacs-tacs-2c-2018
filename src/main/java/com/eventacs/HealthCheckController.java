package com.eventacs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/eventacs")
public class HealthCheckController {

    private static final Logger LOGGER = LoggerFactory.getLogger(HealthCheckController.class);

    @RequestMapping(value = "/health-check" , method = RequestMethod.GET)
    @ResponseBody
    public String getHealthState() {
        LOGGER.info("/eventacs/health-check [GET]");
        return "Status: Ok, App online.";
    }

}
