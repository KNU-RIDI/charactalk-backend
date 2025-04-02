package knu.ridi.charactalk.chat.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import knu.ridi.charactalk.chat.api.dto.ChatRequest;
import knu.ridi.charactalk.chat.api.dto.ChatStreamResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import reactor.core.publisher.Flux;

@Tag(name = "Chat", description = "AI 캐릭터 채팅 API")
public interface ChatControllerDocs {

    @Operation(
            summary = "채팅 메시지 전송",
            description = "사용자가 입력한 메시지를 AI 캐릭터에게 전송합니다.",
            responses = {
                    @ApiResponse(responseCode = "204", description = "메시지 전송 성공"),
                    @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content)
            }
    )
    ResponseEntity<Void> send(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "채팅 요청 메시지",
                    required = true,
                    content = @Content(schema = @Schema(implementation = ChatRequest.class))
            )
            @RequestBody ChatRequest request
    );

    @Operation(
            summary = "채팅 스트리밍 구독",
            description = "해당 멤버 ID의 채팅 스트리밍을 SSE(Server-Sent Events) 방식으로 구독합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "스트리밍 시작", content = @Content(schema = @Schema(implementation = ChatStreamResponse.class)))
            }
    )
    Flux<ServerSentEvent<ChatStreamResponse>> stream(
            @Parameter(description = "멤버 ID", required = true)
            @RequestParam Long memberId
    );
}
