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
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class AIConfig {

    private final ChatMemory chatMemory;
    public AIConfig(ChatMemory chatMemory) {
        this.chatMemory = chatMemory;
    }

    public OllamaChatModel primaryOllamaModel() {
        return createModel("llama3.2", 0.9);
    }

    public OllamaChatModel createModel(String modelName, double weight) {
        OllamaApi ollamaApi = new OllamaApi("http://localhost:11434");

        OllamaOptions ollamaOptions = OllamaOptions.create()
                .withModel(modelName)
                .withTemperature(weight);

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
    public ChatClient chatClient() {
//        ChatClient chatClient = ChatClient.create(primaryOllamaModel());
        ChatClient chatClient = ChatClient.builder(primaryOllamaModel())
                .defaultAdvisors(MessageChatMemoryAdvisor.builder(chatMemory).build())
                .build();
        return chatClient;
    }

}
