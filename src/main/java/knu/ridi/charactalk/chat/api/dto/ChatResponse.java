package knu.ridi.charactalk.chat.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import knu.ridi.charactalk.chat.domain.SenderType;

import java.time.LocalDateTime;

@Schema(description = "채팅 메시지")
public record ChatResponse(
        @Schema(description = "발신자 타입", required = true, example = "CHARACTER or MEMBER")
        SenderType senderType,   // MEMBER, CHARACTER
        @Schema(description = "발신자 이름", required = true, example = "Alice")
        String name,             // 발신자 이름
        String message,          // 전체 메시지
        @Schema(description = "메시지 생성 시간", required = true, example = "2021-10-10T10:00:00")
        LocalDateTime timestamp
) {
}
