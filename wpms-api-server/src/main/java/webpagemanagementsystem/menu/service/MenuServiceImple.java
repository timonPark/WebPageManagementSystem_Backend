package webpagemanagementsystem.menu.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import webpagemanagementsystem.menu.dto.CreateMenuReqDto;
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
  public Menu createMenu(CreateMenuReqDto createMenuReqDto) {
    return menuRepository.save(this.convertCreateMenuReqDtoToMenu(createMenuReqDto));
  }

  @Override
  public Menu updateMenu(Menu menu) {
    return null;
  }

  private Menu convertCreateMenuReqDtoToMenu(CreateMenuReqDto createMenuReqDto) {
    return Menu.builder()
        .name(createMenuReqDto.getName())
        .url(createMenuReqDto.getUrl())
        .orderNo(createMenuReqDto.getOrderNo())
        .isManager(createMenuReqDto.getIsManager())
        .build();
  }
}
