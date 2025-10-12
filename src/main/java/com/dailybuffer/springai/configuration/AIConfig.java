package com.dailybuffer.springai.configuration;

import io.micrometer.observation.ObservationRegistry;
import org.springframework.ai.chat.client.ChatClient;
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

    public OllamaChatModel getOllamaChatModel() {
        OllamaApi ollamaApi = new OllamaApi("http://localhost:11424");

        OllamaOptions ollamaOptions = OllamaOptions.create()
                .withModel("llama3.2")
                .withTemperature(0.7);

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
    public ChatClient chatClient(OllamaChatModel chatClient) {
//        return chatClient;
        return ChatClient.create(chatClient);
    }
}
