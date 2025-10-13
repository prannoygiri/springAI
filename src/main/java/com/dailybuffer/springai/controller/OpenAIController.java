package com.dailybuffer.springai.controller;

import com.dailybuffer.springai.service.SarcasticChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class OpenAIController {

    private final SarcasticChatService sarcasticChatService;

    @Autowired
    public OpenAIController(SarcasticChatService sarcasticChatService) {
        this.sarcasticChatService = sarcasticChatService;
    }

    @GetMapping("/getMoviesbyActor")
    public String actorMovies(@RequestParam String actor) {
        return sarcasticChatService.actorFilms(actor).toString();
    }

    @GetMapping("/prompt")
    public String prompt(@RequestParam String message) {
        return sarcasticChatService.chat(message);
    }
}
