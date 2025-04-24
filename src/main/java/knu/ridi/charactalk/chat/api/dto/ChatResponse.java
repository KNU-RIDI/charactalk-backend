package knu.ridi.charactalk.chat.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import knu.ridi.charactalk.chat.domain.SenderType;

import java.time.LocalDateTime;

@Schema(description = "채팅 메시지")
public record ChatResponse(
    @Schema(description = "발신자 타입", example = "CHARACTER 또는 MEMBER")
    SenderType senderType,
    @Schema(description = "캐릭터 ID. 발신자의 타입이 CHARACTER가 아닌 경우, null", example = "1")
    Long characterId,
    @Schema(description = "메시지", example = "안녕하세요")
    String message,
    @Schema(description = "메시지 생성 시간", example = "2021-10-10T10:00:00")
    LocalDateTime timestamp
) {
}
