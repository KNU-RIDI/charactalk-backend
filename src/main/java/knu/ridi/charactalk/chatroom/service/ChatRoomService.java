package knu.ridi.charactalk.chatroom.service;

import knu.ridi.charactalk.character.domain.Character;
import knu.ridi.charactalk.character.domain.CharacterReader;
import knu.ridi.charactalk.chatroom.api.dto.ChatRoomCardResponse;
import knu.ridi.charactalk.chatroom.domain.ChatRoom;
import knu.ridi.charactalk.chatroom.domain.ChatRoomReader;
import knu.ridi.charactalk.chatroom.domain.ChatRoomWriter;
import knu.ridi.charactalk.chatroom.service.dto.CreateChatRoomCommand;
import knu.ridi.charactalk.chatroom.service.dto.GetChatRoomsCommand;
import knu.ridi.charactalk.member.domain.Member;
import knu.ridi.charactalk.member.domain.MemberReader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ChatRoomService {

    private final ChatRoomWriter chatRoomWriter;
    private final CharacterReader characterReader;
    private final MemberReader memberReader;
    private final ChatRoomReader chatRoomReader;

    @Transactional
    public ChatRoom createChatRoom(CreateChatRoomCommand command) {
        Member member = memberReader.readByEmail(command.email());
        Character character = characterReader.readBy(command.characterId());
        ChatRoom chatRoom = ChatRoom.builder()
            .name(command.name())
            .type(command.type())
            .member(member)
            .character(character)
            .build();
        return chatRoomWriter.write(chatRoom);
    }

    public List<ChatRoomCardResponse> getChatRooms(GetChatRoomsCommand command) {
        return chatRoomReader.readChatRoomsByMemberId(command.memberId());
    }
}
