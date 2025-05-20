package knu.ridi.charactalk.chat.service.dto;

import knu.ridi.charactalk.global.common.cursor.dto.CursorRequest;

public record GetChatHistoryCommand(
        Long chatRoomId,
        Long memberId,
        Long cursor,
        int size
) {
    public CursorRequest toCursorRequest() {
        return CursorRequest.of(cursor, size);
    }
}
