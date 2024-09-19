package webpagemanagementsystem.chat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import webpagemanagementsystem.chat.entity.ChatRoomAndUser;

public interface ChatRoomAndUserRepository  extends JpaRepository<ChatRoomAndUser, Long> {

}
