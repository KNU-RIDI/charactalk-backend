package knu.ridi.charactalk.chat.service;


import knu.ridi.charactalk.chat.Chat;
import knu.ridi.charactalk.chat.ChatRepository;
import knu.ridi.charactalk.chat.dto.ChatRequest;
import knu.ridi.charactalk.chatroom.ChatRoom;
import knu.ridi.charactalk.chatroom.ChatRoomRepository;
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
    private final ChatRoomRepository chatRoomRepository;


    public Chat saveUserChat(ChatRequest request) {
        ChatRoom chatRoom = getChatRoom(request.chatRoomId());
        Chat userChat = Chat.fromUser(request, chatRoom);
        chatRepository.save(userChat);

        return userChat;
    }

    public ChatRoom getChatRoom(Long chatRoomId) {
        log.info("🔍 chatRoomId 조회 요청: {}", chatRoomId);
        return chatRoomRepository.findById(chatRoomId)
                .orElseThrow(() -> {
                    log.error("❌ 채팅방 ID {} 없음!", chatRoomId);
                    return new IllegalArgumentException("채팅방을 찾을 수 없습니다.");
                });
    }
}
