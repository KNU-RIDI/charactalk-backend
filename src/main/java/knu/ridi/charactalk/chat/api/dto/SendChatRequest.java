package knu.ridi.charactalk.chat.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "채팅 전송 요청")
public record SendChatRequest(
    @Schema(description = "메시지", example = "안녕하세요")
    String message
) {
}
