package knu.ridi.charactalk.chat.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import knu.ridi.charactalk.chat.Chat;
import knu.ridi.charactalk.chat.ChatRepository;
import knu.ridi.charactalk.chat.dto.ChatRequest;
import knu.ridi.charactalk.chat.dto.ChatStreamResponse;
import knu.ridi.charactalk.chat.supporter.ChatMessageAssembler;
import knu.ridi.charactalk.chat.supporter.ChatStreamManager;
import knu.ridi.charactalk.chatroom.ChatRoom;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class ChatAsyncService {
    private final WebClient webClient;
    private final ChatStreamManager streamManager;
    private final ChatMessageAssembler assembler;
    private final ChatRepository chatRepository;
    private final ObjectMapper objectMapper;

    public Mono<Void> sendToAI(ChatRequest request, ChatRoom chatRoom) {
        log.info("@@@@@ /chat/send 요청 도착: {}", request);

        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/generate/{roomId}")
                        .queryParam("charId", request.charId())
                        .queryParam("message", request.message())
                        .build(request.chatRoomId().toString())
                )
                .accept(MediaType.TEXT_EVENT_STREAM)
                .retrieve()
                .bodyToFlux(new ParameterizedTypeReference<ServerSentEvent<ChatStreamResponse>>() {})
                .map(ServerSentEvent::data)
                .doOnNext(data -> {
                    log.info("🟢 받은 응답: {}", data);
                    streamManager.push(request.senderId(), data); // 실시간 전달
                })

                .map(token -> assembler.appendAndBuild(request.chatRoomId(), token))
                .filter(Optional::isPresent)
                .map(Optional::get)

                .flatMap(chatResponse -> {
                    Chat chat = Chat.fromAI(chatResponse, chatRoom);

                    return Mono.fromCallable(() -> chatRepository.save(chat))
                            .subscribeOn(Schedulers.boundedElastic())
                            .then();
                })
                .then();
    }

}
