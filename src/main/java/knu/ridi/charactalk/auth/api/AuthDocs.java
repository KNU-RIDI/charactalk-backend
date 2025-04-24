package knu.ridi.charactalk.auth.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.servlet.view.RedirectView;

@Tag(name = "인증")
public interface AuthDocs {

    @Operation(
        summary = "OAuth2.0 로그인",
        description = "OAuth2.0 인증을 통해 사용자를 로그인 시킵니다.",
        responses = {
            @ApiResponse(
                responseCode = "302",
                description = "로그인 페이지로 리다이렉트",
                content = @Content
            ),
        }
    )
    @Parameters({
        @Parameter(
            name = "registrationId",
            description = "OAuth2.0 제공자 ID",
            example = "google",
            required = true
        ),
        @Parameter(
            name = "redirect",
            description = "로그인 후 리다이렉트할 URL",
            example = "https://charactalk.site",
            required = true
        )
    })
    RedirectView login(
        String registrationId,
        String redirect
    );
}
