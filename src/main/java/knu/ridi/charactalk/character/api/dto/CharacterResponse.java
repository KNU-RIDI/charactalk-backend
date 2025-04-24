package knu.ridi.charactalk.character.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import knu.ridi.charactalk.character.domain.Character;

@Schema(description = "캐릭터 응답")
public record CharacterResponse(
    @Schema(description = "캐릭터 ID", example = "1")
    Long characterId,
    @Schema(description = "캐릭터 이름", example = "신데렐라")
    String name,
    @Schema(description = "캐릭터 설명", example = "신데렐라는 아름다운 소녀로, 마법의 도움으로 왕자와 만난다.")
    String description,
    @Schema(description = "캐릭터 이미지 URL", example = "https://example.com/image.jpg")
    String imageUrl
) {

    public static CharacterResponse from(Character character) {
        return new CharacterResponse(
            character.getId(),
            character.getName(),
            character.getDescription(),
            character.getImageUrl()
        );
    }
}
