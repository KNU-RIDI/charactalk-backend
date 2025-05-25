package knu.ridi.charactalk.chatroom.api;

import knu.ridi.charactalk.chatroom.api.dto.CreateChatRoomRequest;
import knu.ridi.charactalk.chatroom.service.dto.CreateChatRoomCommand;
import knu.ridi.charactalk.chatroom.service.dto.GetChatRoomsCommand;
import knu.ridi.charactalk.member.domain.Member;
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

    public GetChatRoomsCommand mapToCommand(
            Member member
    ) {
        return new GetChatRoomsCommand(
            member.getId()
        );
    }
}
