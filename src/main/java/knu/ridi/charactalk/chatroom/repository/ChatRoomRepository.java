package knu.ridi.charactalk.chatroom.repository;

import knu.ridi.charactalk.chatroom.domain.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
}
