package knu.ridi.charactalk.chatroom.service;

import knu.ridi.charactalk.character.domain.Character;
import knu.ridi.charactalk.character.domain.CharacterReader;
import knu.ridi.charactalk.chatroom.domain.ChatRoom;
import knu.ridi.charactalk.chatroom.domain.ChatRoomWriter;
import knu.ridi.charactalk.chatroom.service.dto.CreateChatRoomCommand;
import knu.ridi.charactalk.member.domain.Member;
import knu.ridi.charactalk.member.domain.MemberReader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ChatRoomService {

    private final ChatRoomWriter chatRoomWriter;
    private final CharacterReader characterReader;
    private final MemberReader memberReader;

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
}
