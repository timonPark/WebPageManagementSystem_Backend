package webpagemanagementsystem.menu.service;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import webpagemanagementsystem.common.graphql.ApiResponseFormat;
import webpagemanagementsystem.menu.dto.CreateMenuReqDto;
import webpagemanagementsystem.menu.dto.UpdateMenuReqDto;
import webpagemanagementsystem.menu.dto.UpdateMenuResponseDto;
import webpagemanagementsystem.menu.entity.Menu;
import webpagemanagementsystem.menu.repository.MenuRepository;
import webpagemanagementsystem.user.entity.Users;

@Service
@RequiredArgsConstructor
public class MenuServiceImple implements MenuService{

  private final MenuRepository menuRepository;

  @Override
  public List<Menu> findAll() {
    return menuRepository.findAll();
  }

  @Override
  public Menu createMenu(CreateMenuReqDto createMenuReqDto, Users user) {
    return menuRepository.save(this.convertCreateMenuReqDtoToMenu(createMenuReqDto, user));
  }

  @Transactional
  @Override
  public ApiResponseFormat<UpdateMenuResponseDto> updateMenu(UpdateMenuReqDto updateMenuReqDto, Users user) {
    try {
      Menu meun = menuRepository.findById(updateMenuReqDto.getMenuNo()).orElseThrow();
      this.replaceMenu(meun, updateMenuReqDto, user);
      return ApiResponseFormat.success(
          HttpStatus.OK,
          UpdateMenuResponseDto.builder().result(true).build()
      );
    } catch (NoSuchElementException e) {
      return ApiResponseFormat.error(
          HttpStatus.NOT_FOUND,
          e.getMessage()
      );
    }
  }

  private void replaceMenu(Menu targetMenu, UpdateMenuReqDto inputMenu, Users user) {
    targetMenu.setName(inputMenu.getName());
    targetMenu.setUrl(inputMenu.getUrl());
    targetMenu.setOrderNo(inputMenu.getOrderNo());
    targetMenu.setIsManager(inputMenu.getIsManager());
    targetMenu.setUpdatedNo(user.getUserNo());
  }



  private Menu convertCreateMenuReqDtoToMenu(CreateMenuReqDto createMenuReqDto, Users user) {
    Menu menu = Menu.builder()
        .name(createMenuReqDto.getName())
        .url(createMenuReqDto.getUrl())
        .orderNo(createMenuReqDto.getOrderNo())
        .isManager(createMenuReqDto.getIsManager())
        .build();
    menu.setCreatedNo(user.getUserNo());
    menu.setUpdatedNo(user.getUserNo());
    return menu;
  }
}
