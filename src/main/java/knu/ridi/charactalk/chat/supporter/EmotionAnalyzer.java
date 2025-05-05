package knu.ridi.charactalk.chat.supporter;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class EmotionAnalyzer {

    private final WebClient webClient;

    @Getter
    private static class EmotionResponse {
        private Emotion emotion;
    }

    public Mono<Emotion> analyze(final String message) {
        return webClient.get()
            .uri(builder -> builder.path("/analyze-emotion")
                .queryParam("message", message)
                .build())
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .bodyToMono(EmotionResponse.class)
            .map(EmotionResponse::getEmotion);
    }
}
