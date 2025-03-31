package knu.ridi.charactalk.chat.supporter;

import knu.ridi.charactalk.chat.SenderType;
import knu.ridi.charactalk.chat.dto.ChatResponse;
import knu.ridi.charactalk.chat.dto.ChatStreamResponse;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class ChatMessageAssembler {

    private final Map<String, StringBuilder> buffer = new ConcurrentHashMap<>();
    private final Map<String, LocalDateTime> timestampMap = new ConcurrentHashMap<>();

    public Optional<ChatResponse> appendAndBuild(Long chatRoomId, ChatStreamResponse token) {
        String roomKey = generateRoomKey(chatRoomId, token.name());

        StringBuilder builder = buffer.computeIfAbsent(roomKey, k -> new StringBuilder());
        builder.append(token.token());

        timestampMap.putIfAbsent(roomKey, token.timestamp());

        if (token.isFinal()) {
            ChatResponse complete = new ChatResponse(
                    SenderType.CHARACTER,
                    token.name(),
                    builder.toString(),
                    timestampMap.getOrDefault(roomKey, LocalDateTime.now())  // fallback
            );
            buffer.remove(roomKey);
            timestampMap.remove(roomKey);
            return Optional.of(complete);
        }

        return Optional.empty();
    }

    private String generateRoomKey(Long roomId, String characterName) {
        return roomId + "-" + characterName;
    }
}
