package knu.ridi.charactalk.chatroom.service.dto;

import knu.ridi.charactalk.chatroom.domain.ChatRoomType;

public record CreateChatRoomCommand(
    String email,
    Long characterId,
    String name,
    ChatRoomType type
) {
}
