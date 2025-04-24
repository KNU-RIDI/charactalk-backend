package knu.ridi.charactalk.chat.api;

import knu.ridi.charactalk.auth.service.CharactalkUser;
import knu.ridi.charactalk.chat.api.dto.SendChatRequest;
import knu.ridi.charactalk.chat.service.ChatService;
import knu.ridi.charactalk.chat.api.dto.ChatToken;
import knu.ridi.charactalk.chat.service.dto.SendChatCommand;
import knu.ridi.charactalk.chat.supporter.ChatStreamManager;
import knu.ridi.charactalk.member.domain.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

@Slf4j
@RestController
@RequestMapping("/chat-room")
@RequiredArgsConstructor
public class ChatController implements ChatDocs {

    private final ChatService chatService;
    private final ChatStreamManager streamManager;

    @PostMapping("/{chatRoomId}/send")
    public ResponseEntity<Void> send(
        @AuthenticationPrincipal CharactalkUser user,
        @PathVariable Long chatRoomId,
        @RequestBody SendChatRequest request
    ) {
        Member member = user.getMember();
        SendChatCommand command = new SendChatCommand(
            chatRoomId,
            request.message(),
            user.getMember()
        );
        chatService.send(command)
            .doOnSubscribe(subscription -> log.debug("채팅 전송 시작: {}, 전송자: {}", chatRoomId, member.getId()))
            .doOnError(error -> log.error("채팅 전송 실패: {}", error.getMessage()))
            .subscribe();
        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "/{chatRoomId}/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<ServerSentEvent<ChatToken>> stream(
        @AuthenticationPrincipal CharactalkUser user,
        @PathVariable Long chatRoomId
    ) {
        Member member = user.getMember();
        return streamManager.subscribe(chatRoomId)
            .map(response -> ServerSentEvent.builder(response).build())
            .doOnSubscribe(subscription -> {
                log.debug("채팅 스트림 시작: {}, 구독자: {}", chatRoomId, member.getId());
            })
            .doFinally(signal -> {
                log.debug("채팅 스트림 종료: {}, 신호: {}", chatRoomId, signal);
                streamManager.disconnect(chatRoomId);
            });
    }
}
