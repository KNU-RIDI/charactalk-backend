package knu.ridi.charactalk.chatroom.repository;

import knu.ridi.charactalk.chatroom.api.dto.ChatRoomCardResponse;
import knu.ridi.charactalk.chatroom.domain.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {

    @Query(value = """
    SELECT
        cr.chat_room_id AS chatRoomId,
        c.name AS characterName,
        c.image_url AS characterImageUrl,
        ch.message AS lastMessage,
        DATE_FORMAT(ch.created_at, '%Y-%m-%d %H:%i:%s') AS lastMessageTime
    FROM chat_room cr
    JOIN member m ON m.member_id = cr.member_id
    LEFT JOIN chat ch ON ch.chat_room_id = cr.chat_room_id
        AND ch.created_at = (
            SELECT MAX(created_at)
            FROM chat
            WHERE chat_room_id = cr.chat_room_id
        )
    JOIN characters c ON c.character_id = cr.character_id
    WHERE m.member_id = :memberId
    ORDER BY
        lastMessageTime IS NULL,
        lastMessageTime DESC
    """, nativeQuery = true)
    List<ChatRoomCardResponse> findAllWithLastMessageByMemberId(@Param("memberId") Long memberId);
}
