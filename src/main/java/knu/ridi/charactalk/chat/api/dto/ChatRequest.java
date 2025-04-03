package knu.ridi.charactalk.chat.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@Schema(description = "채팅 요청")
public record ChatRequest(
    @Schema(description = "채팅방 ID", example = "1")
    Long chatRoomId,
    @Schema(description = "보낸 사람 ID (memberId or characterId)", example = "42")
    Long senderId,
    @Schema(description = "AI 캐릭터 ID", example = "cinderella")
    String charId,
    @Schema(description = "메시지", example = "안녕하세요")
    String message,
    @Schema(description = "메시지 전송 시간 (서버 시간)", example = "2025-04-02T15:30:00")
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
