package knu.ridi.charactalk.chatroom.api.dto;

import knu.ridi.charactalk.chatroom.domain.ChatRoomType;

public record CreateChatRoomRequest(
    Long characterId,
    String name,
    ChatRoomType type
) {
}
