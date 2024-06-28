package webpagemanagementsystem.user.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import webpagemanagementsystem.common.entity.BaseTimeEntity;
import webpagemanagementsystem.common.entity.IsUseEnum;
import webpagemanagementsystem.common.entity.YNEnum;

@Entity
@Getter
@NoArgsConstructor
public class Users extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userNo;

    @Column(length = 40, nullable = false)
    private String name;

    @Column(length = 100, nullable = false)
    private String email;

    @Column(length = 100, nullable = true)
    private String password;

    @Column(nullable = false)
    @ColumnDefault("'N'")
    @Enumerated(EnumType.STRING)
    private YNEnum isSocial;

    @Column(nullable = true)
    @Enumerated(EnumType.STRING)
    private SocialType socialType;

    @Column(length = 200, nullable = true)
    String picture;

    @Column(length = 1, nullable = false)
    @ColumnDefault("'Y'")
    @Enumerated(EnumType.STRING)
    private IsUseEnum isUseEnum;



}
