package knu.ridi.charactalk.story.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import knu.ridi.charactalk.story.domain.Story;
import knu.ridi.charactalk.story.domain.StoryType;

@Schema(description = "스토리 카드 응답")
public record StoryCardResponse(
        @Schema(description = "스토리 ID", example = "1")
        Long storyId,
        @Schema(description = "스토리 제목", example = "신데렐라")
        String title,
        @Schema(description = "스토리 타입", example = "WESTERN, EASTERN")
        StoryType storyType,
        @Schema(description = "스토리 이미지 URL", example = "https://example.com/image.jpg")
        String imageUrl
) {
    public static StoryCardResponse from(
        Story story
    ) {
        return new StoryCardResponse(
                story.getId(),
                story.getTitle(),
                story.getStoryType(),
                story.getImageUrl()
        );
    }
}
