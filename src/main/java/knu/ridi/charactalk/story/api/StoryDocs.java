package knu.ridi.charactalk.story.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import knu.ridi.charactalk.story.api.dto.StoryCardResponse;
import knu.ridi.charactalk.story.api.dto.StoryResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Tag(name = "스토리")
public interface StoryDocs {
    @Operation(summary = "스토리 상세 조회", description = "스토리 ID를 통해 하나의 스토리(스토리 디테일 : 제목, 설명, 캐릭터들 등)를 조회합니다.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "스토리 조회 성공"),
    })
    ResponseEntity<StoryResponse> getStory(
        @Parameter(description = "스토리 ID", example = "1") @PathVariable Long storyId
    );

    @Operation(summary = "스토리 목록 조회", description = "스토리 카드 형태(스토리 이미지, 이름)로 전체 스토리 목록을 조회합니다.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "스토리 목록 조회 성공")
    })
    ResponseEntity<List<StoryCardResponse>> getStories();
}
