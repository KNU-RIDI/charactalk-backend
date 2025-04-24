package knu.ridi.charactalk.chatroom.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import knu.ridi.charactalk.chatroom.api.dto.CreateChatRoomRequest;
import knu.ridi.charactalk.chatroom.api.dto.CreateChatRoomResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.core.user.OAuth2User;

@Tag(name = "채팅방")
public interface ChatRoomDocs {

    @Operation(
        summary = "채팅방 생성",
        description = "사용자가 원하는 캐릭터와 대화할 수 있는 채팅방을 생성합니다.",
        responses = {
            @ApiResponse(responseCode = "201", description = "채팅방 생성 성공")
        }
    )
    @RequestBody(
        description = "채팅방 생성 요청",
        required = true,
        content = @Content(schema = @Schema(implementation = CreateChatRoomRequest.class))
    )
    ResponseEntity<CreateChatRoomResponse> createChatRoom(
        OAuth2User user,
        CreateChatRoomRequest request
    );
}
