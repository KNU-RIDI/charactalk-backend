package knu.ridi.charactalk.member.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import knu.ridi.charactalk.auth.service.CharactalkUser;
import knu.ridi.charactalk.member.api.dto.MemberResponse;
import org.springframework.http.ResponseEntity;

@Tag(name = "회원")
public interface MemberDocs {

    @Operation(
        summary = "내 프로필 조회",
        description = "현재 로그인한 사용자의 프로필 정보를 조회합니다.",
        responses = {
            @ApiResponse(responseCode = "200", description = "프로필 조회 성공"),
        }
    )
    ResponseEntity<MemberResponse> getMyProfile(CharactalkUser user);
}
