package com.example.demo.controller;

import com.example.demo.dto.WebSocketInputMessage;
import com.example.demo.dto.WebSocketOutputMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/websocket")
public class WebSocketController {

    @GetMapping
    public String webSocket() {
        return "webSocket";
    }

    @MessageMapping("/message") // Listens for messages sent to "/app/message"
    @SendTo("/topic/messages")  // Sends the response to "/topic/messages"
    public WebSocketOutputMessage sendMessage(WebSocketInputMessage message) {
        return new WebSocketOutputMessage(message.getFrom(), message.getText(), System.currentTimeMillis());
    }

}
