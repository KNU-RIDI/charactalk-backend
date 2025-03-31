package knu.ridi.charactalk.chat.api.dto;

import java.time.LocalDateTime;


public record ChatRequest(
    Long chatRoomId,
    Long senderId,
    String charId,
    String message,
    LocalDateTime timestamp
) {
    public static ChatRequest from(
        Long chatRoomId,
        Long senderId,
        String charId,
        String message,
        LocalDateTime timestamp
    ) {
        return new ChatRequest(
            chatRoomId,
            senderId,
            charId,
            message,
            timestamp
        );
    }
}
