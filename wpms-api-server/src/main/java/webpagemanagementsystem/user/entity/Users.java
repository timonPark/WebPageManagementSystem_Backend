package webpagemanagementsystem.user.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import webpagemanagementsystem.chat.entity.ChatRoomAndUser;
import webpagemanagementsystem.common.entity.BaseTimeEntity;
import webpagemanagementsystem.common.entity.IsUseEnum;
import webpagemanagementsystem.common.entity.YNEnum;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Users extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userNo;

    @Column(length = 40, nullable = false)
    private String name;

    @Column(length = 100, nullable = false)
    private String email;

    @Setter
    @Column(length = 100)
    private String password;

    @Column(nullable = false)
    @ColumnDefault("'N'")
    @Enumerated(EnumType.STRING)
    private YNEnum isSocial;

    @Column(length = 50)
    private String socialId;

    @Column()
    @Enumerated(EnumType.STRING)
    private SocialType socialType;

    @Column(length = 200)
    private String picture;

    @Column(length = 1, nullable = false)
    @ColumnDefault("'U'")
    @Enumerated(EnumType.STRING)
    private IsUseEnum isUse;

    // User와 ChatRoomAndUser 간의 관계 설정 (OneToMany)
    @OneToMany(mappedBy = "user")
    private List<ChatRoomAndUser> chatRoomAndUsers;
}
