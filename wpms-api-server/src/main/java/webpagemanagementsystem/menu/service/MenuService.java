package webpagemanagementsystem.menu.service;

import java.util.List;
import webpagemanagementsystem.menu.dto.MenuResDto;

public interface MenuService {
  public List<MenuResDto> findAll();

}
