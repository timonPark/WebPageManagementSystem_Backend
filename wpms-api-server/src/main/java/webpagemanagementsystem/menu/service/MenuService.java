package webpagemanagementsystem.menu.service;

import java.util.List;
import webpagemanagementsystem.common.graphql.ApiResponseFormat;
import webpagemanagementsystem.menu.dto.CreateMenuReqDto;
import webpagemanagementsystem.menu.dto.IsUseMenuReqDto;
import webpagemanagementsystem.menu.dto.IsUseMenuResponseDto;
import webpagemanagementsystem.menu.dto.UpdateMenuReqDto;
import webpagemanagementsystem.menu.dto.UpdateMenuResponseDto;
import webpagemanagementsystem.menu.entity.Menu;
import webpagemanagementsystem.user.entity.Users;

public interface MenuService {
  public List<Menu> findAll();
  public Menu createMenu(CreateMenuReqDto createMenuReqDto, Users user);
  public ApiResponseFormat<UpdateMenuResponseDto> updateMenu(UpdateMenuReqDto updateMenuReqDto, Users user);
  public ApiResponseFormat<IsUseMenuResponseDto> deleteMenu(IsUseMenuReqDto updateMenuReqDto, Users user);
  public ApiResponseFormat<IsUseMenuResponseDto> unUseMenu(IsUseMenuReqDto unUseMenuReqDto, Users user);

}
