package knu.ridi.charactalk.chat.service;

import knu.ridi.charactalk.chat.api.dto.ChatRequest;
import knu.ridi.charactalk.chat.api.dto.ChatResponse;
import knu.ridi.charactalk.chat.api.dto.ChatStreamResponse;
import knu.ridi.charactalk.chat.domain.Chat;
import knu.ridi.charactalk.chat.domain.ChatWriter;
import knu.ridi.charactalk.chat.supporter.ChatMessageAssembler;
import knu.ridi.charactalk.chat.supporter.ChatStreamManager;
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

@Service
@Slf4j
@RequiredArgsConstructor
public class ChatAsyncService {

    private final WebClient webClient;

    private final ChatStreamManager streamManager;
    private final ChatMessageAssembler assembler;

    private final ChatWriter chatWriter;
    private final ChatRoomReader chatRoomReader;

    public Mono<Void> sendToAI(ChatRequest request) {
        ChatRoom chatRoom = chatRoomReader.readBy(request.chatRoomId());
        return webClient.get()
            .uri(builder -> buildUri(builder, request))
            .accept(MediaType.TEXT_EVENT_STREAM)
            .retrieve()
            .bodyToFlux(getFluxType())
            .mapNotNull(ServerSentEvent::data)
            .doOnNext(data -> handleAIResponse(request.senderId(), data))
            .flatMap(token -> assembler.appendAndBuild(request.chatRoomId(), token))
            .flatMap(response -> saveChat(response, chatRoom))
            .then();
    }

    private static URI buildUri(UriBuilder builder, ChatRequest request) {
        return builder.path("/generate/{roomId}")
            .queryParam("charId", request.charId())
            .queryParam("message", request.message())
            .build(request.chatRoomId().toString());
    }

    private static ParameterizedTypeReference<ServerSentEvent<ChatStreamResponse>> getFluxType() {
        return new ParameterizedTypeReference<>() {};
    }

    private void handleAIResponse(Long senderId, ChatStreamResponse data) {
        log.debug("받은 응답: {}", data);
        streamManager.push(senderId, data);
    }

    private Mono<Void> saveChat(ChatResponse chatResponse, ChatRoom chatRoom) {
        Chat chat = Chat.fromAI(chatResponse, chatRoom);
        return Mono.fromCallable(() -> chatWriter.write(chat))
            .subscribeOn(Schedulers.boundedElastic())
            .then();
    }
}
