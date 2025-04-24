package knu.ridi.charactalk.chat.supporter;

import java.time.LocalDateTime;

public record ChatStreamToken(
    String token,
    boolean isFinal,
    LocalDateTime timestamp
) {
}
