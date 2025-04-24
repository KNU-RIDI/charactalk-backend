package knu.ridi.charactalk.chat.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@Schema(description = "채팅 토큰")
public record ChatToken(
    @Schema(description = "캐릭터 ID", example = "1")
    Long characterId,
    @Schema(description = "채팅 토큰(메시지 일부)")
    String token,
    @Schema(description = "마지막 토큰 여부")
    boolean isFinal,
    @Schema(description = "채팅 토큰 생성 시간", example = "2021-10-10T10:00:00")
    LocalDateTime timestamp
) {
}
