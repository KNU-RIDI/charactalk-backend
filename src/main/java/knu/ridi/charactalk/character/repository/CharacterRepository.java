package knu.ridi.charactalk.character.repository;

import knu.ridi.charactalk.character.domain.Character;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CharacterRepository extends JpaRepository<Character, Long> {
}
