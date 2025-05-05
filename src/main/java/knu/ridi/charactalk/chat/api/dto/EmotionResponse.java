package knu.ridi.charactalk.chat.api.dto;

import knu.ridi.charactalk.chat.supporter.Emotion;

public record EmotionResponse(
    Emotion emotion
) {
    public static EmotionResponse of(final Emotion emotion) {
        return new EmotionResponse(emotion);
    }
}