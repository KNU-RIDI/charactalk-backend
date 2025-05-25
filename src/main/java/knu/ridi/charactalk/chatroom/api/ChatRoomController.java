package knu.ridi.charactalk.chatroom.api;

import knu.ridi.charactalk.auth.service.CharactalkUser;
import knu.ridi.charactalk.chatroom.api.dto.ChatRoomCardResponse;
import knu.ridi.charactalk.chatroom.api.dto.CreateChatRoomRequest;
import knu.ridi.charactalk.chatroom.api.dto.CreateChatRoomResponse;
import knu.ridi.charactalk.chatroom.service.ChatRoomService;
import knu.ridi.charactalk.chatroom.service.dto.CreateChatRoomCommand;
import knu.ridi.charactalk.chatroom.service.dto.GetChatRoomsCommand;
import knu.ridi.charactalk.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/chat-room")
public class ChatRoomController implements ChatRoomDocs {

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

    @GetMapping
    public ResponseEntity<List<ChatRoomCardResponse>> getChatRooms(
            @AuthenticationPrincipal CharactalkUser user
            ) {

        Member member = user.getMember();

        GetChatRoomsCommand command = commandMapper.mapToCommand(member);
        List<ChatRoomCardResponse> responses = chatRoomService.getChatRooms(command);
        return ResponseEntity.status(HttpStatus.OK).body(responses);
    }

}