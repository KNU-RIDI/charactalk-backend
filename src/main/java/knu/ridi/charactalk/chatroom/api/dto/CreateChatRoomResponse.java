package knu.ridi.charactalk.chatroom.api.dto;

import knu.ridi.charactalk.character.domain.Character;
import knu.ridi.charactalk.character.api.dto.CharacterResponse;
import knu.ridi.charactalk.chatroom.domain.ChatRoom;

public record CreateChatRoomResponse(
    Long chatRoomId,
    CharacterResponse character
) {

    public static CreateChatRoomResponse from(ChatRoom chatRoom) {
        Long chatRoomId = chatRoom.getId();
        Character character = chatRoom.getCharacter();
        return new CreateChatRoomResponse(
            chatRoomId,
            CharacterResponse.from(character)
        );
    }
}
