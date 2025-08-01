package com.custom.app.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SpaForwardingController {

    @RequestMapping(value = {
            "/login",
            "/edit",
            "/"
    })
    public String forwardReactRoutes() {
        return "forward:/index.html";
    }
}