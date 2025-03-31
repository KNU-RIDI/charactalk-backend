package knu.ridi.charactalk.chat.repository;

import knu.ridi.charactalk.chat.domain.Chat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRepository extends JpaRepository<Chat, Long> {
}
