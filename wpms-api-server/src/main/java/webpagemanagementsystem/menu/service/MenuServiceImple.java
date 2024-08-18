package webpagemanagementsystem.menu.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import webpagemanagementsystem.menu.entity.Menu;
import webpagemanagementsystem.menu.repository.MenuRepository;

@Service
@RequiredArgsConstructor
public class MenuServiceImple implements MenuService{

  private final MenuRepository menuRepository;

  @Override
  public List<Menu> findAll() {
    return menuRepository.findAll();
  }

  @Override
  public Menu createMenu() {
    return null;
  }

  @Override
  public Menu updateMenu(Menu menu) {
    return null;
  }
}
