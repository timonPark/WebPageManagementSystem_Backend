package webpagemanagementsystem.common.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity extends BaseTimeEntity {

  @Column(length = 1, nullable = false)
  @ColumnDefault("'U'")
  @Enumerated(EnumType.STRING)
  private IsUseEnum isUse = IsUseEnum.U;

  @Column(nullable = false)
  private Long createdNo;

  @Column(nullable = true)
  private Long updatedNo;
}
