package knu.ridi.charactalk.chat.service;

import knu.ridi.charactalk.chat.supporter.SpeechToTextConverter;
import knu.ridi.charactalk.chat.supporter.TextToSpeechConverter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

@Slf4j
@Service
@RequiredArgsConstructor
public class SpeechService {

    private final SpeechToTextConverter sttConverter;
    private final TextToSpeechConverter ttsConverter;

    private final Map<String, ByteArrayOutputStream> audioBuffers = new ConcurrentHashMap<>();

    public void startStreaming(final String sessionId) {
        log.debug("🎤 [{}] STT 세션 시작", sessionId);
        audioBuffers.put(sessionId, new ByteArrayOutputStream());
    }

    public void streamAudio(final String sessionId, final ByteBuffer audioBuffer) {
        final ByteArrayOutputStream buffer = audioBuffers.get(sessionId);

        if (buffer == null) {
            log.warn("⚠️ [{}] STT 세션이 초기화되지 않았습니다.", sessionId);
            return ;
        }

        byte[] chunk = new byte[audioBuffer.remaining()];
        audioBuffer.get(chunk);

        try {
            buffer.write(chunk);
            log.debug("🎤 [{}] 오디오 버퍼에 {} bytes 누적", sessionId, chunk.length);
        } catch (final IOException exception) {
            log.warn("❌ [{}] 오디오 버퍼링 실패", sessionId, exception);
        }
    }

    public void stopStreaming(
        final String sessionId,
        final Long characterId,
        final Consumer<ByteBuffer> consumer
    ) {
        final ByteArrayOutputStream buffer = audioBuffers.remove(sessionId);

        log.debug("🎤 [{}] STT 변환 시작", sessionId);
        final String message = sttConverter.convert(buffer);
        log.debug("🎤 [{}] STT 변환 완료: {}", sessionId, message);

        log.debug("🎤 [{}] TTS 변환 시작", sessionId);
        ttsConverter.convert(sessionId, characterId, message)
            .doOnNext(consumer)
            .subscribe();
        log.debug("🎤 [{}] TTS 변환 완료 및 STT 세션 종료", sessionId);
    }
}