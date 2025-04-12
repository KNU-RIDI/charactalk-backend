package knu.ridi.charactalk.chat.api;

import knu.ridi.charactalk.chat.api.dto.ChatRequest;
import knu.ridi.charactalk.chat.api.dto.ChatStreamResponse;
import knu.ridi.charactalk.chat.service.ChatAsyncService;
import knu.ridi.charactalk.chat.supporter.ChatStreamManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

@Slf4j
@RestController
@RequestMapping("/chat")
@RequiredArgsConstructor
public class ChatController implements ChatControllerDocs {

    private final ChatAsyncService chatAsyncService;
    private final ChatStreamManager streamManager;

    @PostMapping("/send")
    public ResponseEntity<Void> send(@RequestBody ChatRequest request) {
        chatAsyncService.sendToAI(request)
            .doOnSubscribe(s -> log.debug("채팅 전송 시작"))
            .subscribe();
        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<ServerSentEvent<ChatStreamResponse>> stream(@RequestParam Long memberId) {
        return streamManager.subscribe(memberId)
            .map(response -> ServerSentEvent.builder(response).build())
            .doFinally(signal -> {
                log.debug("채팅 스트림 종료: {}, 신호: {}", memberId, signal);
                streamManager.disconnect(memberId);
            });
    }
}
