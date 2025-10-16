package com.dailybuffer.springai.configuration;

import io.micrometer.observation.ObservationRegistry;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.model.function.FunctionCallback;
import org.springframework.ai.model.function.FunctionCallbackContext;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.ai.ollama.api.OllamaApi;
import org.springframework.ai.ollama.api.OllamaOptions;
import org.springframework.ai.ollama.management.ModelManagementOptions;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class AIConfig {

    private final ChatMemory llamaMemory;
    private final ChatMemory lavaMemroy;
    private final ChatMemory llavaMemory;

    public AIConfig(@Qualifier("llamaMemory") ChatMemory llamaMemory,
                    @Qualifier("llavaMemory") ChatMemory llavaMemroy, ChatMemory llavaMemory) {
        this.llamaMemory = llamaMemory;
        this.lavaMemroy = llavaMemroy;
        this.llavaMemory = llavaMemory;
    }

    // Llama 3.2 Model
    public OllamaChatModel llamaModel() {
        return createModel("llama3.2", 0.9);
    }

    // llava Model
    public OllamaChatModel llavaModel() {
        return createModel("llava", 0.9);
    }

    public OllamaChatModel createModel(String modelName, double temperature) {
        OllamaApi ollamaApi = new OllamaApi("http://localhost:11434");

        OllamaOptions ollamaOptions = OllamaOptions.create()
                .withModel(modelName)
                .withTemperature(temperature);

        // Empty/default helper dependencies
        FunctionCallbackContext callbackContext = new FunctionCallbackContext();
        List<FunctionCallback> callbacks = List.of();
        ObservationRegistry registry = ObservationRegistry.NOOP;
        ModelManagementOptions managementOptions = ModelManagementOptions.defaults();

        return new OllamaChatModel(
                ollamaApi,
                ollamaOptions,
                callbackContext,
                callbacks,
                registry,
                managementOptions);
    }


    @Bean
    @Qualifier("llamaChatClient")
    public ChatClient llamaChatClient() {
        ChatClient chatClient = ChatClient.builder(llamaModel())
                .defaultAdvisors(MessageChatMemoryAdvisor.builder(llamaMemory).build())
                .build();
        return chatClient;
    }

    @Bean
    @Qualifier("llavaChatClient")
    public ChatClient llavaChatClient() {
        ChatClient chatClient = ChatClient.builder(llavaModel())
                .defaultAdvisors(MessageChatMemoryAdvisor.builder(llavaMemory).build())
                .build();
        return chatClient;
    }
}
