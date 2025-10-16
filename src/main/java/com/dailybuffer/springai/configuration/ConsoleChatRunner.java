package com.dailybuffer.springai.configuration;

import com.dailybuffer.springai.service.SarcasticChatService;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Scanner;

@Configuration
public class ConsoleChatRunner {

    private final SarcasticChatService sarcasticChatService;

    public ConsoleChatRunner(SarcasticChatService sarcasticChatService) {
        this.sarcasticChatService = sarcasticChatService;
    }

    @Bean
    public CommandLineRunner startChatLoop() {

        return args -> {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Oh hoe hoe! Who Stands there\n: ");

            while(true) {

                System.out.print("You:  ");
                String userInput = scanner.nextLine();

                if(userInput.equalsIgnoreCase("exit")) {
                    System.out.println("Goodbye!");
                    break;
                }

                try {
                    String response = sarcasticChatService.chat(userInput);
                    System.out.println("AI : " + response + "\n");
                } catch (Exception e) {
                    System.err.println("ERROR : " +e.getMessage());
                }
            }
            scanner.close();
        };
    }
}
