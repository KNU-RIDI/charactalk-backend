package knu.ridi.charactalk.chat.supporter;

import knu.ridi.charactalk.chat.api.dto.ChatResponse;
import knu.ridi.charactalk.chat.api.dto.ChatStreamResponse;
import knu.ridi.charactalk.chat.domain.SenderType;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class ChatMessageAssembler {

    private final Map<String, StringBuilder> buffer = new ConcurrentHashMap<>();
    private final Map<String, LocalDateTime> timestampMap = new ConcurrentHashMap<>();

    public Mono<ChatResponse> appendAndBuild(Long chatRoomId, ChatStreamResponse token) {
        if (!token.isFinal()) return Mono.empty();

        String roomKey = generateRoomKey(chatRoomId, token.name());
        StringBuilder builder = buffer.computeIfAbsent(roomKey, k -> new StringBuilder());
        builder.append(token.token());
        timestampMap.putIfAbsent(roomKey, token.timestamp());

        ChatResponse complete = new ChatResponse(
            SenderType.CHARACTER,
            token.name(),
            builder.toString(),
            timestampMap.getOrDefault(roomKey, LocalDateTime.now())  // fallback
        );

        buffer.remove(roomKey);
        timestampMap.remove(roomKey);

        return Mono.just(complete);
    }

    private String generateRoomKey(Long roomId, String characterName) {
        return roomId + "-" + characterName;
    }
}
