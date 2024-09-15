package webpagemanagementsystem.chat.entity;

import jakarta.persistence.*;
import lombok.*;
import webpagemanagementsystem.common.entity.BaseEntity;
import webpagemanagementsystem.menu.entity.Menu;

import java.util.List;

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

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Menu> children;
}
