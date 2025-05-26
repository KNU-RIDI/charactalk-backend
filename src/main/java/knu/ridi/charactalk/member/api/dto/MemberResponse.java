package knu.ridi.charactalk.member.api.dto;

import knu.ridi.charactalk.member.domain.Gender;
import knu.ridi.charactalk.member.domain.Member;
import knu.ridi.charactalk.member.domain.MemberRole;

public record MemberResponse(
    Long id,
    String email,
    Gender gender,
    String birth,
    MemberInfoResponse info,
    MemberRole role
) {
    public static MemberResponse from(final Member member) {
        return new MemberResponse(
            member.getId(),
            member.getEmail(),
            member.getGender(),
            member.getBirth(),
            MemberInfoResponse.from(member),
            member.getRole()
        );
    }
}

record MemberInfoResponse(
    String picture,
    String name
) {
    public static MemberInfoResponse from(final Member member) {
        return new MemberInfoResponse(
            member.getPicture(),
            member.getName()
        );
    }
}