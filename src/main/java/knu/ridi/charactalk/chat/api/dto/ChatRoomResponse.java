package knu.ridi.charactalk.chat.api.dto;

import knu.ridi.charactalk.chatroom.domain.ChatRoom;
import knu.ridi.charactalk.chatroom.domain.ChatRoomType;

public record ChatRoomResponse(
        Long chatRoomId,
        String name,
        ChatRoomType type,
        Long characterId,
        String characterName,
        String characterDescription,
        String characterImageUrl
) {
    public static ChatRoomResponse from(ChatRoom chatRoom) {
        return new ChatRoomResponse(
                chatRoom.getId(),
                chatRoom.getName(),
                chatRoom.getType(),
                chatRoom.getCharacter().getId(),
                chatRoom.getCharacter().getName(),
                chatRoom.getCharacter().getDescription(),
                chatRoom.getCharacter().getImageUrl()
        );
    }
}
