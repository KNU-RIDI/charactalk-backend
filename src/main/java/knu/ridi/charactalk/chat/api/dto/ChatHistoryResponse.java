package knu.ridi.charactalk.chat.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import knu.ridi.charactalk.chat.domain.Chat;
import knu.ridi.charactalk.chat.domain.SenderType;

import java.time.LocalDateTime;

@Schema(description = "db 저장된 채팅 메시지 조회용 dto")
public record ChatHistoryResponse(
        @Schema(description = "채팅 ID", example = "1")
        Long chatId,
        @Schema(description = "발신자 타입", example = "CHARACTER 또는 MEMBER")
        SenderType senderType,
        @Schema(description = "캐릭터 ID. 발신자의 타입이 CHARACTER가 아닌 경우, null", example = "1")
        Long characterId,
        @Schema(description = "메시지", example = "안녕하세요")
        String message,
        @Schema(description = "메시지 생성 시간", example = "2021-10-10T10:00:00")
        LocalDateTime timestamp
) {
        public static ChatHistoryResponse from(Chat chat) {
                return new ChatHistoryResponse(
                        chat.getId(),
                        chat.getSenderType(),
                        chat.getSenderType() == SenderType.CHARACTER ? chat.getSenderId() : null,
                        chat.getMessage(),
                        chat.getCreatedAt()
                );
        }
}
