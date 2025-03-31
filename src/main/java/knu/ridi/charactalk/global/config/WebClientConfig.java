package knu.ridi.charactalk.global.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Value("${charactalk.ai-server.url}")
    private String aiServerUrl;

    @Bean
    public WebClient webClient(WebClient.Builder builder) {
        return builder
                .baseUrl(aiServerUrl)
                .build();
    }
}
