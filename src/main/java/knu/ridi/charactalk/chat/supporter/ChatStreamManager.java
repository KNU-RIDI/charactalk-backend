package knu.ridi.charactalk.chat.supporter;

import knu.ridi.charactalk.chat.api.dto.ChatToken;
import knu.ridi.charactalk.chatroom.domain.ChatRoom;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class ChatStreamManager {

    private final Map<Long, Sinks.Many<ChatToken>> sinks = new ConcurrentHashMap<>();

    public Flux<ChatToken> subscribe(Long chatRoomId) {
        return sinks.computeIfAbsent(chatRoomId, k ->
            Sinks.many().multicast().onBackpressureBuffer()
        ).asFlux();
    }

    public void push(ChatRoom chatRoom, ChatStreamToken streamToken) {
        ChatToken token = new ChatToken(
            chatRoom.getCharacter().getId(),
            streamToken.token(),
            streamToken.isFinal(),
            streamToken.timestamp()
        );
        Optional.ofNullable(sinks.get(chatRoom.getId()))
            .ifPresent(s -> s.tryEmitNext(token));
    }

    public void disconnect(Long chatRoomId) {
        Optional.ofNullable(sinks.remove(chatRoomId))
            .ifPresent(Sinks.Many::tryEmitComplete);
    }
}
