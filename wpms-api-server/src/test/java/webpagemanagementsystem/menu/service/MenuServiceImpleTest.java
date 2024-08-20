package webpagemanagementsystem.menu.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import webpagemanagementsystem.common.entity.IsUseEnum;
import webpagemanagementsystem.common.entity.YNEnum;
import webpagemanagementsystem.menu.entity.Menu;
import webpagemanagementsystem.menu.repository.MenuRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@SpringBootTest
class MenuServiceImpleTest {

  @MockBean(name="menuRepository")
  private MenuRepository menuRepository;

  @DisplayName("menuList 조회 - 성공")
  @Test
  void test(){
    // given
    Menu menu = Menu.builder()
        .menuNo(1L)
        .name("Home")
        .url("")
        .orderNo(1)
        .isManager(YNEnum.N)
        .build();

    menu.setIsUse(IsUseEnum.U);
    menu.setCreatedNo(1L);
    menu.setUpdatedNo(1L);
    menu.setCreatedAt(LocalDateTime.now());
    menu.setUpdatedAt(LocalDateTime.now());

    Menu menu2 = Menu.builder()
        .menuNo(2L)
        .name("Page")
        .url("")
        .orderNo(2)
        .isManager(YNEnum.Y)
        .build();

    menu.setIsUse(IsUseEnum.U);
    menu.setCreatedNo(1L);
    menu.setUpdatedNo(1L);
    menu.setCreatedAt(LocalDateTime.now());
    menu.setUpdatedAt(LocalDateTime.now());

    List<Menu> menus = Arrays.asList(menu, menu2);

    given(menuRepository.findAll()).willReturn(menus);

    // when & then
    List<Menu> result = menuRepository.findAll();

    assertThat(result).isEqualTo(menus);

  }

  @DisplayName("menu 생성 - 실패")
  @Test
  void test2(){
    // given

    // when & then
    assertThat("").isNull();

  }

  @DisplayName("menu 생성 - 성공")
  @Test
  void test3(){
    // given

    // when & then
    assertThat("").isNull();

  }

  @DisplayName("menu 업데이트 - 실패")
  @Test
  void test4(){
    // given

    // when & then
    assertThat("").isNull();

  }

  @DisplayName("menu 업데이트 - 성공")
  @Test
  void test5(){
    // given

    // when & then
    assertThat("").isNull();

  }

}