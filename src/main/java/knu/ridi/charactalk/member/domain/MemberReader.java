package knu.ridi.charactalk.member.domain;

import knu.ridi.charactalk.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberReader {

    private static final String MEMBER_NOT_FOUND = "해당 회원이 존재하지 않습니다.";
    private final MemberRepository memberRepository;

    public Member readByEmail(final String email) {
        return memberRepository.findByEmail(email)
            .orElseThrow(() -> new IllegalArgumentException(MEMBER_NOT_FOUND));
    }

    public boolean existsByEmail(final String email) {
        return memberRepository.existsByEmail(email);
    }
}
