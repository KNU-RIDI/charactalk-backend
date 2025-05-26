package knu.ridi.charactalk.member.api;

import knu.ridi.charactalk.auth.service.CharactalkUser;
import knu.ridi.charactalk.member.api.dto.MemberResponse;
import knu.ridi.charactalk.member.domain.Member;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/members")
public class MemberQueryController implements MemberDocs {

    @GetMapping("/me")
    public ResponseEntity<MemberResponse> getMyProfile(
        @AuthenticationPrincipal final CharactalkUser user
    ) {
        final Member member = user.getMember();
        return ResponseEntity.ok(MemberResponse.from(member));
    }
}
