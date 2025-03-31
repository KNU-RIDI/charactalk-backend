package knu.ridi.charactalk.chat.service;


import knu.ridi.charactalk.chat.api.dto.ChatRequest;
import knu.ridi.charactalk.chat.domain.Chat;
import knu.ridi.charactalk.chat.repository.ChatRepository;
import knu.ridi.charactalk.chatroom.domain.ChatRoom;
import knu.ridi.charactalk.chatroom.domain.ChatRoomReader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ChatSyncService {

    private final ChatRepository chatRepository;
    private final ChatRoomReader chatRoomReader;

    public Chat saveUserChat(ChatRequest request) {
        ChatRoom chatRoom = chatRoomReader.readBy(request.chatRoomId());
        Chat userChat = Chat.fromUser(request, chatRoom);
        chatRepository.save(userChat);
        return userChat;
    }
}
