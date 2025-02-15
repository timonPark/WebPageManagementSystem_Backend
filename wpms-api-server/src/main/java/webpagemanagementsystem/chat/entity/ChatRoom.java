package webpagemanagementsystem.chat.entity;

import jakarta.persistence.*;
import lombok.*;
import webpagemanagementsystem.common.entity.BaseEntity;

import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatRoom extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long chatRoomNo;

    @OneToMany(mappedBy = "chatRoom")
    private List<ChatRoomAndUser> chatRoomAndUsers;
}
