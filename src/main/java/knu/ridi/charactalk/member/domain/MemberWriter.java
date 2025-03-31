package knu.ridi.charactalk.member.domain;

import knu.ridi.charactalk.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MemberWriter {

    private final MemberRepository repository;

    public Member write(final Member member) {
        return repository.save(member);
    }
}
