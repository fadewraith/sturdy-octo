package com.realtimenotificationdemo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class NotificationController {

    private static final Logger log = LoggerFactory.getLogger(NotificationController.class);

    @MessageMapping("/sendMessage")
    @SendTo("/topic/notifications") // broker where the msgs will be published
    public String sendMessage(String msg) {
//        System.out.println("message: " + msg);
        log.info("message: {}", msg);
        return msg;
    }
}
