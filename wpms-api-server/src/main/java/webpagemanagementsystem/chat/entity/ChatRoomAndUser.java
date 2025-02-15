package webpagemanagementsystem.chat.entity;

import jakarta.persistence.*;
import lombok.*;
import webpagemanagementsystem.common.entity.BaseEntity;
import webpagemanagementsystem.user.entity.Users;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatRoomAndUser extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long chatRoomAndUserNo;

    // ChatRoom과의 관계 설정
    @ManyToOne
    @JoinColumn(name = "chat_room_no")
    private ChatRoom chatRoom;

    // User와의 관계 설정 (OneToMany -> ManyToOne으로 수정)
    @ManyToOne
    @JoinColumn(name = "user_no")
    private Users user;
}