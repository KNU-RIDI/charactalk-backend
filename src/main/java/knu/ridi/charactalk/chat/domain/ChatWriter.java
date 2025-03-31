package knu.ridi.charactalk.chat.domain;

import knu.ridi.charactalk.chat.repository.ChatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ChatWriter {

    private final ChatRepository chatRepository;

    public Chat write(Chat chat) {
        return chatRepository.save(chat);
    }
}
