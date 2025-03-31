package knu.ridi.charactalk.chat.dto;

import knu.ridi.charactalk.chat.SenderType;

import java.time.LocalDateTime;

public record ChatResponse(
        SenderType senderType,   // MEMBER, CHARACTER
        String name,             // 발신자 이름
        String message,          // 전체 메시지
        LocalDateTime timestamp
) {
}
