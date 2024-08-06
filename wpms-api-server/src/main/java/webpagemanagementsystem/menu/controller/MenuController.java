package webpagemanagementsystem.menu.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import webpagemanagementsystem.menu.service.MenuService;

@Controller
@RequestMapping("/menu")
@RequiredArgsConstructor
public class MenuController {
  private final MenuService menuService;

}
