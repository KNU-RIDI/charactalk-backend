package knu.ridi.charactalk.character.domain;

import knu.ridi.charactalk.character.repository.CharacterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CharacterReader {

    private final CharacterRepository repository;

    public Character readBy(Long characterId) {
        return repository.findById(characterId)
            .orElseThrow(() -> new IllegalArgumentException("해당 캐릭터가 존재하지 않습니다."));
    }
}
