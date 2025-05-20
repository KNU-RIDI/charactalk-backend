package knu.ridi.charactalk.chat.service.dto;

public record GetChatRoomCommand(
        Long chatRoomId,
        Long memberId
) {
}
