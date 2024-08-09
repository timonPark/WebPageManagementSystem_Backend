package webpagemanagementsystem.menu.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import webpagemanagementsystem.menu.entity.Menu;
import webpagemanagementsystem.menu.repository.MenuRepository;
import webpagemanagementsystem.menu.type.MenuType;

@Service
@RequiredArgsConstructor
public class MenuServiceImple implements MenuService{

  private final MenuRepository menuRepository;

  @Override
  public List<Menu> findAll() {
    return menuRepository.findAll();
  }

  public List<MenuType> convertMenuToMenuResDto(List<Menu> menus) {
    return menus.stream().map(
                        menu -> new MenuType(
                            menu.getMenuNo(),
                            menu.getName(),
                            menu.getUrl(),
                            menu.getChildren().stream().map(this::convertMenuToMenuType).toList(),
                            menu.getOrderNo(),
                            menu.getIsManager(),
                            menu.getIsUse(),
                            menu.getCreatedNo(),
                            menu.getUpdatedNo(),
                            menu.getCreatedAt(),
                            menu.getUpdatedAt()
                        )
    ).toList();
  }

  private MenuType convertMenuToMenuType(Menu menu){
    return new MenuType(
        menu.getMenuNo(),
        menu.getName(),
        menu.getUrl(),
        menu.getChildren().stream().map(this::convertMenuToMenuType).toList(),
        menu.getOrderNo(),
        menu.getIsManager(),
        menu.getIsUse(),
        menu.getCreatedNo(),
        menu.getUpdatedNo(),
        menu.getCreatedAt(),
        menu.getUpdatedAt()
    );
  }
}
