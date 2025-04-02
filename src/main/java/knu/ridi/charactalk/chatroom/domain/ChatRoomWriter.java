package knu.ridi.charactalk.chatroom.domain;

import knu.ridi.charactalk.chatroom.repository.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ChatRoomWriter {

    private final ChatRoomRepository repository;

    public ChatRoom write(final ChatRoom chatRoom) {
        return repository.save(chatRoom);
    }
}
