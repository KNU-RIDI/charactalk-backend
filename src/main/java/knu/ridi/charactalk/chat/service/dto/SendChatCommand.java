package knu.ridi.charactalk.chat.service.dto;

import knu.ridi.charactalk.member.domain.Member;

public record SendChatCommand(
    Long chatRoomId,
    String message,
    Member member
) {
}
