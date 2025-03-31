package knu.ridi.charactalk.member.repository;

import knu.ridi.charactalk.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
