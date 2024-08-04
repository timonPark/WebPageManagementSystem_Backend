package webpagemanagementsystem.menu.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import webpagemanagementsystem.common.entity.BaseEntity;
import webpagemanagementsystem.common.entity.YNEnum;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Menu extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long menuNo;

  @Column(length = 40, nullable = false)
  private String name;

  @Column(length = 255, nullable = true)
  private String url;

  @ManyToOne
  @JoinColumn(name = "parent_menu_no")
  private Menu parent;

  @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Menu> children;

  @Column(nullable = false)
  private Integer orderNo;

  @Column(nullable = false)
  @ColumnDefault("'N'")
  @Enumerated(EnumType.STRING)
  private YNEnum isManager;
}
