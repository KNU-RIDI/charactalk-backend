package knu.ridi.charactalk.chat.supporter;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.nio.ByteBuffer;

@Component
@RequiredArgsConstructor
public class TextToSpeechConverter {

    private final WebClient webClient;

    public Flux<ByteBuffer> convert(
        final String roomId,
        final Long characterId,
        final String message
    ) {
        return webClient.get()
            .uri(builder -> builder.path("/speech/{roomId}")
                .queryParam("characterId", characterId)
                .queryParam("message", message)
                .build(roomId))
            .accept(MediaType.parseMediaType("audio/L16"))
            .retrieve()
            .bodyToFlux(ByteBuffer.class);
    }
}
