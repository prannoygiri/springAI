package com.dailybuffer.springai.configuration;

import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.InMemoryChatMemory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ChatMemoryConfig {

    //Memory for Llama3.2
    @Bean
    @Qualifier("llamaMemory")
    public ChatMemory llamaMemory() {
        return new InMemoryChatMemory();
    }

    @Bean
    @Qualifier("llavaMemory")
    public ChatMemory llavaMemory() {
        return new InMemoryChatMemory();
    }



}
