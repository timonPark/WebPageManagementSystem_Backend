package webpagemanagementsystem.menu.service;

import java.util.List;
import webpagemanagementsystem.menu.entity.Menu;

public interface MenuService {
  public List<Menu> findAll();
  public Menu createMenu();
  public Menu updateMenu(Menu menu);

}
