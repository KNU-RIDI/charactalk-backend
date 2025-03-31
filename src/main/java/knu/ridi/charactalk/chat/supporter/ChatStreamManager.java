package knu.ridi.charactalk.chat.supporter;

import knu.ridi.charactalk.chat.api.dto.ChatStreamResponse;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class ChatStreamManager {

    private final Map<Long, Sinks.Many<ChatStreamResponse>> sinks = new ConcurrentHashMap<>();

    public Flux<ChatStreamResponse> subscribe(Long memberId) {
        return sinks.computeIfAbsent(memberId,
            k -> Sinks.many().multicast().onBackpressureBuffer()
        ).asFlux();
    }

    public void push(Long memberId, ChatStreamResponse response) {
        Optional.ofNullable(sinks.get(memberId)).ifPresent(s -> s.tryEmitNext(response));
    }

    public void disconnect(Long memberId) {
        sinks.remove(memberId);
    }
}
