package webpagemanagementsystem.menu.dto;

import java.util.List;
import lombok.Builder;
import lombok.Data;
import webpagemanagementsystem.menu.entity.Menu;

@Data
@Builder
public class MenuResDto {
  private Long menuNo;
  private String name;
  private String url;
  private List<Menu> children;
  private Integer orderNo;

}
