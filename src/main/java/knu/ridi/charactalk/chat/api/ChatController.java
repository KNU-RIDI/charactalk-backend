package knu.ridi.charactalk.chat.api;

import knu.ridi.charactalk.chat.dto.ChatRequest;
import knu.ridi.charactalk.chat.dto.ChatStreamResponse;
import knu.ridi.charactalk.chat.service.ChatAsyncService;
import knu.ridi.charactalk.chat.service.ChatSyncService;
import knu.ridi.charactalk.chat.supporter.ChatStreamManager;
import knu.ridi.charactalk.chatroom.ChatRoom;
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
public class ChatController {
    private final ChatAsyncService chatAsyncService;
    private final ChatSyncService chatSyncService;
    private final ChatStreamManager streamManager;

    @PostMapping("/send")
    public ResponseEntity<Void> send(@RequestBody ChatRequest request) {
        System.out.println("컨트롤러 호출됨 @@@@");
        ChatRoom chatRoom = chatSyncService.getChatRoom(request.chatRoomId());
        log.info("💬 저장 완료, AI 요청 전송 준비");

        // 비동기로 FastAPI 서버로 요청 보내기
        chatAsyncService.sendToAI(request, chatRoom)
                .doOnSubscribe(s -> log.info("채팅 전송 시작@@@@"))
                .subscribe();

        return ResponseEntity.noContent().build();  // 응답을 바로 리턴
    }

    @GetMapping(value = "/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<ServerSentEvent<ChatStreamResponse>> stream(@RequestParam Long memberId) {
        return streamManager.subscribe(memberId)
                .map(response -> ServerSentEvent.builder(response).build());
    }

}
