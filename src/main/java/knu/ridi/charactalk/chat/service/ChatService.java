package knu.ridi.charactalk.chat.service;

import knu.ridi.charactalk.character.domain.Character;
import knu.ridi.charactalk.chat.api.dto.ChatResponse;
import knu.ridi.charactalk.chat.domain.Chat;
import knu.ridi.charactalk.chat.domain.ChatWriter;
import knu.ridi.charactalk.chat.domain.SenderType;
import knu.ridi.charactalk.chat.service.dto.SendChatCommand;
import knu.ridi.charactalk.chat.supporter.ChatMessageAssembler;
import knu.ridi.charactalk.chat.supporter.ChatStreamManager;
import knu.ridi.charactalk.chat.supporter.ChatStreamToken;
import knu.ridi.charactalk.chatroom.domain.ChatRoom;
import knu.ridi.charactalk.chatroom.domain.ChatRoomReader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriBuilder;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.net.URI;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatService {

    private final WebClient webClient;

    private final ChatStreamManager streamManager;
    private final ChatMessageAssembler assembler;

    private final ChatWriter chatWriter;
    private final ChatRoomReader chatRoomReader;

    public Mono<Void> send(SendChatCommand command) {
        ChatRoom chatRoom = chatRoomReader.readBy(command.chatRoomId());
        Chat chat = Chat.builder()
            .chatRoom(chatRoom)
            .message(command.message())
            .senderId(command.member().getId())
            .senderType(SenderType.MEMBER)
            .build();
        chatWriter.write(chat);
        return generateChat(command, chatRoom)
            .doOnError(error -> log.error("❌ [{}] 채팅 생성 실패", command.chatRoomId(), error))
            .doOnSuccess(unused -> log.debug("✅ [{}] 채팅 생성 완료", command.chatRoomId()));
    }

    private Mono<Void> generateChat(
        SendChatCommand command,
        ChatRoom chatRoom
    ) {
        Character character = chatRoom.getCharacter();
        return webClient.get()
            .uri(builder -> buildUri(builder, command, character))
            .accept(MediaType.TEXT_EVENT_STREAM)
            .retrieve()
            .bodyToFlux(SSEChatTokenType())
            .mapNotNull(ServerSentEvent::data)
            .doOnNext(token -> streamManager.push(chatRoom, token))
            .flatMap(token -> assembler.appendAndBuild(chatRoom, token))
            .flatMap(response -> saveChat(chatRoom, response))
            .then();
    }

    private static URI buildUri(
        UriBuilder builder,
        SendChatCommand command,
        Character character
    ) {
        return builder.path("/generate/{roomId}")
            .queryParam("characterId", character.getId())
            .queryParam("message", command.message())
            .build(command.chatRoomId());
    }

    private static ParameterizedTypeReference<ServerSentEvent<ChatStreamToken>> SSEChatTokenType() {
        return new ParameterizedTypeReference<>() {};
    }

    private Mono<Void> saveChat(
        ChatRoom chatRoom,
        ChatResponse response
    ) {
        Character character = chatRoom.getCharacter();
        Chat chat = Chat.builder()
            .chatRoom(chatRoom)
            .message(response.message())
            .senderId(character.getId())
            .senderType(SenderType.CHARACTER)
            .build();
        return Mono.fromCallable(() -> chatWriter.write(chat))
            .subscribeOn(Schedulers.boundedElastic())
            .then();
    }
}
