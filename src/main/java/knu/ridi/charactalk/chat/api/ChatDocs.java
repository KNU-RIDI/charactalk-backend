package knu.ridi.charactalk.chat.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import knu.ridi.charactalk.auth.service.CharactalkUser;
import knu.ridi.charactalk.chat.api.dto.SendChatRequest;
import knu.ridi.charactalk.chat.api.dto.ChatToken;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.ServerSentEvent;
import reactor.core.publisher.Flux;

@Tag(name = "채팅")
public interface ChatDocs {

    @Operation(
        summary = "채팅 메시지 전송",
        description = "사용자가 입력한 메시지를 채팅방에 전송합니다.",
        responses = {
            @ApiResponse(responseCode = "204", description = "메시지 전송 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content)
        }
    )
    @RequestBody(
        description = "채팅 전송 요청",
        required = true,
        content = @Content(schema = @Schema(implementation = SendChatRequest.class))
    )
    ResponseEntity<Void> send(
        CharactalkUser user,
        Long chatRoomId,
        SendChatRequest request
    );

    @Operation(
        summary = "채팅 스트리밍 구독",
        description = "해당 채팅방의 채팅 스트리밍을 SSE(Server-Sent Events) 방식으로 구독합니다.",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "스트리밍 시작",
                content = @Content(
                    schema = @Schema(implementation = ChatToken.class)
                )
            )
        }
    )
    Flux<ServerSentEvent<ChatToken>> stream(
        CharactalkUser user,
        Long chatRoomId
    );
}
