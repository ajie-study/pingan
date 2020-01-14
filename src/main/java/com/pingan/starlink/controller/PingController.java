package com.pingan.starlink.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class PingController {
    @RequestMapping(value = "/_ping", method = RequestMethod.GET)
    public String ping() {
        return "Connected.";
    }
    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public String hello() {
        return "Hello Eireve Devops.";
    }
}
