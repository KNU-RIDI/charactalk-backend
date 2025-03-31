package knu.ridi.charactalk.chat.dto;

import java.time.LocalDateTime;

public record ChatStreamResponse(
        String name,             // 캐릭터 이름
        String token,            // 토큰 또는 문장 일부
        boolean isFinal,         // 마지막 토큰 여부
        LocalDateTime timestamp
) {
}
