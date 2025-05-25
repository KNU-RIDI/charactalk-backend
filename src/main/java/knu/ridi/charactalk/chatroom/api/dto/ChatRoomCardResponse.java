package knu.ridi.charactalk.chatroom.api.dto;


import io.swagger.v3.oas.annotations.media.Schema;

public record ChatRoomCardResponse(
        @Schema(description = "채팅방 ID", example = "1")
        Long chatRoomId,
        @Schema(description = "캐릭터 이름", example = "cinderella")
        String characterName,
        @Schema(description = "캐릭터 이미지 URL", example = "/static/images/cinderella.png")
        String characterImageUrl,
        @Schema(description = "마지막 메시지 내용", example = "안녕하세요!")
        String lastMessage,
        @Schema(description = "마지막 메시지 시간", example = "2025-05-07 18:52:45")
        String lastMessageTime
) {

}
