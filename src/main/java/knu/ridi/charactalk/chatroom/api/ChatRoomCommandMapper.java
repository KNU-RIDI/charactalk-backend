package knu.ridi.charactalk.chatroom.api;

import knu.ridi.charactalk.chatroom.api.dto.CreateChatRoomRequest;
import knu.ridi.charactalk.chatroom.service.dto.CreateChatRoomCommand;
import org.springframework.stereotype.Component;

@Component
public class ChatRoomCommandMapper {

    public CreateChatRoomCommand mapToCommand(
        String email,
        CreateChatRoomRequest request
    ) {
        return new CreateChatRoomCommand(
            email,
            request.characterId(),
            request.name(),
            request.type()
        );
    }
}
