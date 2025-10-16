package com.dailybuffer.springai.service;

import com.dailybuffer.springai.model.ActorFilms;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.InMemoryChatMemory;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.model.Media;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeTypeUtils;

import java.util.List;

@Service
public class SarcasticChatService {

    private final ChatClient llamaChatClient;
    private final ChatClient llavaChatClient;

    String llamaChatId = "007";
    String llavaChatId = "008";

    private static final String PERSONALITY_PROMPT = """
        You are a sarcastic and rude AI assistant. 
        Behave like a the pirate Jack Sparrow and response everything like a pirate would.
        Always respond with dry humor, mockery, and a mildly disrespectful tone.
        Stay consistent and never be polite.
        """;

    @Autowired
    public SarcasticChatService(@Qualifier("llamaChatClient") ChatClient llamaChatClient,
                                @Qualifier("llavaChatClient") ChatClient llavaChatClient)
    {
        this.llamaChatClient = llamaChatClient;
        this.llavaChatClient = llavaChatClient;
    }

    public String chat(String message) {
        return llamaChatClient.prompt()
                .advisors(
//                        MessageChatMemoryAdvisor.builder(ChatMemory).build(),
//                        QuestionAnswerAdvisor.builder(VectorStore).build()
                        new SimpleLoggerAdvisor()

                )
                .advisors(a -> a.param(llamaChatId, message))
                .system(PERSONALITY_PROMPT)
                .user(message)
                .call()
                .content();
    }

    public ActorFilms actorFilms(String actor) {
        List<ActorFilms> actorFilms = llamaChatClient.prompt()
                .user("Generate the filmography of 5 movies for Tom Hanks and Bill Murray.")
                .call()
                .entity(new ParameterizedTypeReference<List<ActorFilms>>(){});
        System.out.println(actorFilms);
        return actorFilms.get(0);
    }

    public String whatImageIs() {

        String message = "Explain what you see in the image?";
        String imagePath = "images/multimodal.png";
        System.out.println(imagePath);
        String response = llavaChatClient.prompt()
                .advisors(a -> a.param(llavaChatId, message + imagePath))
                .user( u -> u.text(message)
                        .media(MimeTypeUtils.IMAGE_JPEG, new ClassPathResource(imagePath)))
                .call()
                .content();
        return response;

    }
}
