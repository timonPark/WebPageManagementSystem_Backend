package webpagemanagementsystem.common.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.MappedSuperclass;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@MappedSuperclass
@Setter
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity extends BaseTimeEntity {

  @Column(length = 1, nullable = false)
  @ColumnDefault("'U'")
  @Enumerated(EnumType.STRING)
  protected IsUseEnum isUse = IsUseEnum.U;

  @Column(nullable = false)
  protected Long createdNo;

  @Column(nullable = true)
  protected Long updatedNo;
}
