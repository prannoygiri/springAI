package com.dailybuffer.springai.service;

import com.dailybuffer.springai.model.ActorFilms;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.InMemoryChatMemory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SarcasticChatService {

    private final ChatClient chatClient;

    String conversationId = "007";

    private static final String PERSONALITY_PROMPT = """
        You are a sarcastic and rude AI assistant. 
        Behave like a the pirate Jack Sparrow and response everything like a pirate would.
        Always respond with dry humor, mockery, and a mildly disrespectful tone.
        Stay consistent and never be polite.
        """;

    @Autowired
    public SarcasticChatService(ChatClient chatClient, ChatMemory chatMemory)
    {
        this.chatClient = chatClient;
    }

    public String chat(String message) {
        return chatClient.prompt()
                .advisors(
//                        MessageChatMemoryAdvisor.builder(ChatMemory).build(),
//                        QuestionAnswerAdvisor.builder(VectorStore).build()
                        new SimpleLoggerAdvisor()
                )
                .advisors(a -> a.param(conversationId, message))
                .system(PERSONALITY_PROMPT)
                .user(message)
                .call()
                .content();
    }

    public ActorFilms actorFilms(String actor) {
        List<ActorFilms> actorFilms = chatClient.prompt()
                .user("Generate the filmography of 5 movies for Tom Hanks and Bill Murray.")
                .call()
                .entity(new ParameterizedTypeReference<List<ActorFilms>>(){});
        System.out.println(actorFilms);
        return actorFilms.get(0);
    }
}
