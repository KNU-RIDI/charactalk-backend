package knu.ridi.charactalk.story.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import knu.ridi.charactalk.character.dto.CharacterResponse;
import knu.ridi.charactalk.story.domain.Story;
import knu.ridi.charactalk.story.domain.StoryType;

import java.util.List;

@Schema(description = "스토리 디테일 응답 DTO")
public record StoryResponse(
    @Schema(description = "스토리 ID", example = "1")
    Long storyId,
    @Schema(description = "스토리 제목", example = "신데렐라")
    String title,
    @Schema(description = "스토리 설명", example = "신데렐라의 이야기")
    String description,
    @Schema(description = "스토리 타입", example = "WESTERN, EASTERN")
    StoryType storyType,
    @Schema(description = "스토리 이미지 URL", example = "https://example.com/image.jpg")
    String imageUrl,
    @Schema(description = "스토리에 등장하는 캐릭터 목록")
    List<CharacterResponse> characters,
    @Schema(description = "스토리 태그 목록", example = "[\"사랑\", \"모험\"]")
    List<String> tags
) {

    public static StoryResponse from(Story story) {
        List<CharacterResponse> characters = story.getCharacters().stream()
            .map(CharacterResponse::from)
            .toList();

        List<String> tagNames = story.getStoryTags().stream()
            .map(storyTag -> storyTag.getTag().getName())
            .toList();

        return new StoryResponse(
            story.getId(),
            story.getTitle(),
            story.getDescription(),
            story.getStoryType(),
            story.getImageUrl(),
            characters,
            tagNames
        );
    }
}
