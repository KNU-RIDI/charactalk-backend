package knu.ridi.charactalk.chatroom.api.dto;

import knu.ridi.charactalk.chatroom.domain.ChatRoom;

public record CreateChatRoomResponse(
    Long chatRoomId
) {

    public static CreateChatRoomResponse from(ChatRoom chatRoom) {
        Long chatRoomId = chatRoom.getId();
        return new CreateChatRoomResponse(chatRoomId);
    }
}
