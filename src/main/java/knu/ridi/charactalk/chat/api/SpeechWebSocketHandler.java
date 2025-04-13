package knu.ridi.charactalk.chat.api;

import knu.ridi.charactalk.chat.service.SpeechService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.BinaryWebSocketHandler;

import java.io.IOException;
import java.nio.ByteBuffer;

@Slf4j
@ConditionalOnProperty(name = "gcp.speech.enabled", havingValue = "true")
@Component
@RequiredArgsConstructor
public class SpeechWebSocketHandler extends BinaryWebSocketHandler {

    private final SpeechService speechService;

    @Override
    public void afterConnectionEstablished(final WebSocketSession session) {
        log.debug("🔌 WebSocket 연결됨: {}", session.getId());
    }

    @Override
    protected void handleTextMessage(final WebSocketSession session, final TextMessage message) {
        final String payload = message.getPayload();
        if (payload.equals("start")) {
            speechService.startStreaming(session.getId());
        } else if (payload.equals("stop")) {
            speechService.stopStreaming(session.getId(), audioStream -> {
                try {
                    session.sendMessage(new BinaryMessage(audioStream));
                } catch (final IOException exception) {
                    log.warn("❌ [{}] STT 결과 전송 실패", session.getId(), exception);
                }
            });
        }
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