package webpagemanagementsystem.menu.service;

import java.util.List;
import webpagemanagementsystem.menu.dto.CreateMenuReqDto;
import webpagemanagementsystem.menu.entity.Menu;
import webpagemanagementsystem.user.entity.Users;

public interface MenuService {
  public List<Menu> findAll();
  public Menu createMenu(CreateMenuReqDto createMenuReqDto, Users user);
  public Menu updateMenu(Menu menu);

}
