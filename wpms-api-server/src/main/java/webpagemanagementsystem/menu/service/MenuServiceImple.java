package webpagemanagementsystem.menu.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import webpagemanagementsystem.menu.repository.MenuRepository;

@Service
@RequiredArgsConstructor
public class MenuServiceImple implements MenuService{

  private final MenuRepository menuRepository;
}
