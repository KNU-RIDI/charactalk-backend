package knu.ridi.charactalk.chat.domain;

import jakarta.persistence.*;
import knu.ridi.charactalk.global.domain.BaseTimeEntity;
import knu.ridi.charactalk.chat.api.dto.ChatRequest;
import knu.ridi.charactalk.chat.api.dto.ChatResponse;
import knu.ridi.charactalk.chatroom.domain.ChatRoom;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Chat extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chat_id")
    private Long id;

    private String message;

    private Long senderId;

    @Enumerated(EnumType.STRING)
    private SenderType senderType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_room_id")
    private ChatRoom chatRoom;

    @Builder
    public Chat(String message, Long senderId, SenderType senderType, ChatRoom chatRoom) {
        this.message = message;
        this.senderId = senderId;
        this.senderType = senderType;
        this.chatRoom = chatRoom;
    }

    public static Chat fromUser(ChatRequest chatRequest, ChatRoom chatRoom) {
        return Chat.builder()
            .chatRoom(chatRoom)
            .message(chatRequest.message())
            .senderId(chatRequest.senderId())
            .senderType(SenderType.MEMBER)
            .build();
    }

    public static Chat fromAI(ChatResponse response, ChatRoom chatRoom) {
        return Chat.builder()
            .chatRoom(chatRoom)
            .message(response.message())
            .senderId(0L) // 나중에 캐릭터 생성 생기면 해당 값으로 변경
            .senderType(SenderType.CHARACTER)
            .build();
    }
}
