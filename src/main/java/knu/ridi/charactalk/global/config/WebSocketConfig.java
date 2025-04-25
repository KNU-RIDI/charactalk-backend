package knu.ridi.charactalk.global.config;

import knu.ridi.charactalk.chat.api.SpeechWebSocketHandler;
import knu.ridi.charactalk.global.WebSocketAttributeResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
@RequiredArgsConstructor
public class WebSocketConfig implements WebSocketConfigurer {

    private final SpeechWebSocketHandler speechWebSocketHandler;
    private final WebSocketAttributeResolver webSocketAttributeResolver;

    @Override
    public void registerWebSocketHandlers(final WebSocketHandlerRegistry registry) {
        registry.addHandler(speechWebSocketHandler, "/ws/speech")
            .addInterceptors(webSocketAttributeResolver)
            .setAllowedOrigins("*");
    }
}