package knu.ridi.charactalk.chat.api;

import knu.ridi.charactalk.chat.service.SpeechToTextService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.BinaryWebSocketHandler;

import java.io.IOException;
import java.nio.ByteBuffer;

@Slf4j
@Component
@RequiredArgsConstructor
public class SpeechWebSocketHandler extends BinaryWebSocketHandler {

    private final SpeechToTextService sttService;

    @Override
    public void afterConnectionEstablished(final WebSocketSession session) {
        log.debug("🔌 WebSocket 연결됨: {}", session.getId());
    }

    @Override
    protected void handleTextMessage(final WebSocketSession session, final TextMessage message) {
        final String payload = message.getPayload();
        if (payload.equals("start")) {
            sttService.startStreaming(session.getId());
        } else if (payload.equals("stop")) {
            sttService.stopStreaming(session.getId(), transcript -> {
                try {
                    session.sendMessage(new TextMessage(transcript));
                } catch (final IOException exception) {
                    log.warn("❌ [{}] STT 결과 전송 실패", session.getId(), exception);
                }
            });
        }
    }

    @Override
    protected void handleBinaryMessage(final WebSocketSession session, final BinaryMessage message) {
        final ByteBuffer audioData = message.getPayload();
        sttService.streamAudio(session.getId(), audioData);
    }

    @Override
    public void afterConnectionClosed(final WebSocketSession session, final CloseStatus status) {
        log.debug("🔌 WebSocket 연결 종료: {}", session.getId());
    }
}