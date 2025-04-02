package knu.ridi.charactalk.chat.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@Schema(description = "채팅 스트림 응답")
public record ChatStreamResponse(
    String name,             // 캐릭터 이름
    String token,            // 토큰 또는 문장 일부
    boolean isFinal,         // 마지막 토큰 여부
    LocalDateTime timestamp
) {
}
