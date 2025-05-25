package knu.ridi.charactalk.chatroom.api.dto;


public record ChatRoomCardResponse(
        Long chatRoomId,
        String characterName,
        String characterImageUrl,
        String lastMessage,
        String lastMessageTime
) {

}
