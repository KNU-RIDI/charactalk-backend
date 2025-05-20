package knu.ridi.charactalk.chat.repository;

import knu.ridi.charactalk.chat.domain.Chat;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ChatRepository extends JpaRepository<Chat, Long> {
    @Query("""
        SELECT c FROM Chat c
        WHERE c.chatRoom.id = :roomId
        AND (:cursor IS NULL OR c.id < :cursor)
        ORDER BY c.id DESC
    """)
    List<Chat> findByChatRoomIdWithCursor(Long roomId, Long cursor, Pageable pageable);
}
