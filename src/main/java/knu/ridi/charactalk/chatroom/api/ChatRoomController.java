package knu.ridi.charactalk.chatroom.api;

import knu.ridi.charactalk.chatroom.api.dto.CreateChatRoomRequest;
import knu.ridi.charactalk.chatroom.api.dto.CreateChatRoomResponse;
import knu.ridi.charactalk.chatroom.service.ChatRoomService;
import knu.ridi.charactalk.chatroom.service.dto.CreateChatRoomCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/chatroom")
public class ChatRoomController {

    private final ChatRoomCommandMapper commandMapper;
    private final ChatRoomService chatRoomService;

    @PostMapping
    public ResponseEntity<CreateChatRoomResponse> createChatRoom(
        @AuthenticationPrincipal OAuth2User user,
        @RequestBody CreateChatRoomRequest request
    ) {
        String email = user.getName();
        CreateChatRoomCommand command = commandMapper.mapToCommand(email, request);
        CreateChatRoomResponse response = CreateChatRoomResponse.from(
            chatRoomService.createChatRoom(command)
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
