package webpagemanagementsystem.menu.type;

import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import webpagemanagementsystem.common.entity.IsUseEnum;
import webpagemanagementsystem.common.entity.YNEnum;

@Getter
@AllArgsConstructor
public class MenuType {
  private final Long menuNo;
  private final String name;
  private final String url;
  private final List<MenuType> children;
  private final Integer orderNo;
  private final YNEnum isManager;
  private final IsUseEnum isUse;
  private final Long createdNo;
  private final Long updatedNo;
  private final LocalDateTime createdAt;
  private final LocalDateTime updatedAt;
}
