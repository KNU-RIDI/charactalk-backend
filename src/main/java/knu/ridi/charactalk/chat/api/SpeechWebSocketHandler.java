package knu.ridi.charactalk.chat.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import knu.ridi.charactalk.chat.api.dto.EmotionResponse;
import knu.ridi.charactalk.chat.service.SpeechService;
import knu.ridi.charactalk.chat.supporter.Emotion;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;

import java.io.IOException;
import java.nio.ByteBuffer;

@Slf4j
@Component
@RequiredArgsConstructor
public class SpeechWebSocketHandler extends AbstractWebSocketHandler {

    private final ObjectMapper objectMapper;
    private final SpeechService speechService;

    @Override
    public void afterConnectionEstablished(final WebSocketSession session) {
        log.debug("🔌 WebSocket 연결됨: {}", session.getId());
    }

    @Override
    protected void handleTextMessage(final WebSocketSession session, final TextMessage message) {
        final String payload = message.getPayload();
        switch (payload) {
            case "start":
                handleStartStreaming(session);
                break;
            case "stop":
                handleStopStreaming(session);
                break;
            default:
                log.error("Unrecognized message payload: {}", payload);
                break;
        }
    }

    private Long getChatRoomIdFromSession(final WebSocketSession session) {
        return Long.parseLong((String) session.getAttributes().get("chatRoomId"));
    }

    private void handleStartStreaming(final WebSocketSession session) {
        speechService.startStreaming(session.getId());
    }

    private void handleStopStreaming(final WebSocketSession session) {
        final Long chatRoomId = getChatRoomIdFromSession(session);
        speechService.stopStreaming(session.getId(), chatRoomId)
            .subscribe(
                data -> sendMessage(session, data),
                exception -> handleError(session, exception)
            );
    }

    private void sendMessage(final WebSocketSession session, final Object data) {
        try {
            final WebSocketMessage<?> message = toMessage(data);
            session.sendMessage(message);
        } catch (final IOException exception) {
            handleError(session, exception);
        }
    }

    private WebSocketMessage<?> toMessage(final Object data) {
        if (data instanceof ByteBuffer audioChunk) {
            return audioChunkToMessage(audioChunk);
        }
        if (data instanceof Emotion emotion) {
            return emotionToMessage(emotion);
        }
        if (data instanceof String text) {
            return new TextMessage(text);
        }
        return null;
    }

    private BinaryMessage audioChunkToMessage(final ByteBuffer audioChunk) {
        return new BinaryMessage(audioChunk);
    }

    private TextMessage emotionToMessage(final Emotion emotion) {
        try {
            final String json = objectMapper.writeValueAsString(EmotionResponse.of(emotion));
            return new TextMessage(json);
        } catch (final JsonProcessingException exception) {
            log.error("❌ JSON 변환 실패", exception);
            return new TextMessage("");
        }
    }

    private void handleError(final WebSocketSession session, final Throwable error) {
        log.error("❌ [{}] STT 변환 실패", session.getId(), error);
    }

    @Override
    protected void handleBinaryMessage(final WebSocketSession session, final BinaryMessage message) {
        final ByteBuffer audioData = message.getPayload();
        speechService.streamAudio(session.getId(), audioData);
    }

    @Override
    public void afterConnectionClosed(final WebSocketSession session, final CloseStatus status) {
        log.debug("🔌 WebSocket 연결 종료: {}", session.getId());
    }
}