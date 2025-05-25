package knu.ridi.charactalk.chatroom.domain;

import knu.ridi.charactalk.chatroom.api.dto.ChatRoomCardResponse;
import knu.ridi.charactalk.chatroom.repository.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ChatRoomReader {

    private final ChatRoomRepository chatRoomRepository;

    public ChatRoom readBy(Long chatRoomId) {
        return chatRoomRepository.findById(chatRoomId)
            .orElseThrow(() -> new IllegalArgumentException("채팅방을 찾을 수 없습니다."));
    }

    public List<ChatRoomCardResponse> readChatRoomsByMemberId(Long memberId) {
        return chatRoomRepository.findAllWithLastMessageByMemberId(memberId);
    }
}
