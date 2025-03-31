package knu.ridi.charactalk.chatroom;

import jakarta.persistence.*;
import knu.ridi.charactalk.base.BaseTimeEntity;
import knu.ridi.charactalk.character.Character;
import knu.ridi.charactalk.member.entity.Member;
import lombok.AccessLevel;
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
}
