package webpagemanagementsystem.menu.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import webpagemanagementsystem.common.response.ApiResponse;
import webpagemanagementsystem.menu.dto.MenuResDto;
import webpagemanagementsystem.menu.entity.Menu;
import webpagemanagementsystem.menu.service.MenuService;

@Controller
@RequestMapping("/menu")
@RequiredArgsConstructor
public class MenuController {
  private final MenuService menuService;

  @GetMapping("/list")
  public ApiResponse<List<MenuResDto>> findAll() {
//    return ApiResponse.createSuccess(menuService.findAll());
    return null;
  }

}
