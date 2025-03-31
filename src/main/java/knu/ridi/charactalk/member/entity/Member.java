package knu.ridi.charactalk.member.entity;

import jakarta.persistence.*;
import knu.ridi.charactalk.base.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    private String nickname;

    private String email;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private String birth;
}

