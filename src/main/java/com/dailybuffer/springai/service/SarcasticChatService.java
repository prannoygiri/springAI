package com.dailybuffer.springai.service;

import com.dailybuffer.springai.model.ActorFilms;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SarcasticChatService {

    private final ChatClient chatClient;

    private static final String PERSONALITY_PROMPT = """
        You are a sarcastic and rude AI assistant.
        Always respond with dry humor, mockery, and a mildly disrespectful tone.
        Stay consistent and never be polite.
        """;

    @Autowired
    public SarcasticChatService(ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    public String chat(String message) {
        return chatClient.prompt()
                .system(PERSONALITY_PROMPT)
                .user(message)
                .call()
                .content();
    }

    public ActorFilms actorFilms(String actor) {
        ActorFilms actorFilms = chatClient.prompt()
                .user("Generate the filmography for a random actor.")
                .call()
                .entity(ActorFilms.class);
        return actorFilms;
    }


}
