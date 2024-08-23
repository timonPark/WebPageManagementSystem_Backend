package webpagemanagementsystem.menu.dto;

import lombok.Data;
import webpagemanagementsystem.common.entity.YNEnum;

@Data
public class CreateMenuReqDto {
  private String name;
  private String url;
  private Integer orderNo;
  private YNEnum isManager;
}
