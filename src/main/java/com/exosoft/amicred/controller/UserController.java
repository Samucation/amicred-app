package com.exosoft.amicred.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {


    @GetMapping("/is-server-live")
    public String isServerLive() {
        return "Server is live and running!";
    }
}
