package knu.ridi.charactalk.chatroom.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import knu.ridi.charactalk.chatroom.domain.ChatRoomType;

@Schema(description = "채팅방 생성 요청")
public record CreateChatRoomRequest(
    @Schema(description = "캐릭터 ID", example = "1")
    Long characterId,
    @Schema(description = "채팅방 이름", example = "채팅방1")
    String name,
    @Schema(description = "채팅방 종류. SINGLE(1:1) 또는 GROUP(1:N)", example = "SINGLE")
    ChatRoomType type
) {
}
