package com.dailybuffer.springai.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class OpenAIController {

    private final ChatClient chatClient;

    @Autowired
    public OpenAIController(ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    @GetMapping("/prompt")
    public String prompt() {
        String response = chatClient.prompt().user("hello").call().content();  //chatClient.prompt("Hello!").call().content();
        System.out.println(response);
        return response;

    }
}
