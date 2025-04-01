package knu.ridi.charactalk.chatroom.domain;

import jakarta.persistence.*;
import knu.ridi.charactalk.global.domain.BaseTimeEntity;
import knu.ridi.charactalk.character.domain.Character;
import knu.ridi.charactalk.member.domain.Member;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChatRoom extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chat_room_id")
    private Long id;

    @Column(name = "char_room_name")
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "char_room_type")
    private ChatRoomType type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "character_id")
    private Character character;

    @Builder
    public ChatRoom(String name, ChatRoomType type, Member member, Character character) {
        this.name = name;
        this.type = type;
        this.member = member;
        this.character = character;
    }
}
