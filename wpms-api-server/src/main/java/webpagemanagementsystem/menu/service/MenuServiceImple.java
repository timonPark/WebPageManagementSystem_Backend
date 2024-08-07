package webpagemanagementsystem.menu.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import webpagemanagementsystem.menu.dto.MenuResDto;
import webpagemanagementsystem.menu.entity.Menu;
import webpagemanagementsystem.menu.repository.MenuRepository;

@Service
@RequiredArgsConstructor
public class MenuServiceImple implements MenuService{

  private final MenuRepository menuRepository;

  @Override
  public List<MenuResDto> findAll() {
    return convertMenuToMenuResDto(menuRepository.findAll());
  }

  public List<MenuResDto> convertMenuToMenuResDto(List<Menu> menus) {
    return menus.stream().map(
                        menu -> MenuResDto.builder()
                              .menuNo(menu.getMenuNo())
                              .name(menu.getName())
                              .url(menu.getUrl())
                              .children(menu.getChildren())
                              .orderNo(menu.getOrderNo())
                              .build()
                        ).toList();
  }
}
