package webpagemanagementsystem.chat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import webpagemanagementsystem.chat.entity.ChatRoom;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {

}
