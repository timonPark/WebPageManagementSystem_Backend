package webpagemanagementsystem.menu.service;

import java.util.List;
import webpagemanagementsystem.menu.dto.CreateMenuReqDto;
import webpagemanagementsystem.menu.entity.Menu;

public interface MenuService {
  public List<Menu> findAll();
  public Menu createMenu(CreateMenuReqDto createMenuReqDto);
  public Menu updateMenu(Menu menu);

}
