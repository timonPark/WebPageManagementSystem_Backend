package webpagemanagementsystem.user.controller;


import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.util.NoSuchElementException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import webpagemanagementsystem.common.entity.IsUseEnum;
import webpagemanagementsystem.common.entity.YNEnum;
import webpagemanagementsystem.user.dto.FindByEmailResponse;
import webpagemanagementsystem.user.entity.SocialType;
import webpagemanagementsystem.user.entity.Users;
import webpagemanagementsystem.user.service.UserService;

@ExtendWith(SpringExtension.class)
@WebMvcTest(UserController.class)

class UserControllerTest {

    @Autowired
    MockMvc mvc;

    @MockBean
    UserService userService;

    @DisplayName("User API 중 이메일로 계정 검색 실패")
    @Test
    public void test() throws Exception {
        // given
        String email = "m11111@naver.com2";

        given(userService.findByEmail(email)).willReturn(null);
        // when

        // then
        mvc.perform(get("/user/email/"+ email))
            .andExpect(status().is2xxSuccessful());
    }

    @DisplayName("User API 중 이메일로 계정 검색 실패")
    @Test
    public void test2() throws Exception {
        // given
        String email = "m11111@naver.com";
        Users user = Users.builder()
            .userNo(3L)
            .name("박종훈")
            .email(email)
            .password(null)
            .isSocial(YNEnum.Y)
            .socialId("18346737826")
            .socialType(SocialType.kakao)
            .picture("")
            .isUse(IsUseEnum.U)
            .build();
        FindByEmailResponse findByEmailResponse = new FindByEmailResponse(user);
        given(userService.findByEmail(email)).willReturn(user);
        given(userService.convertUsersToFindByEmailResponse(user)).willReturn(findByEmailResponse);

        // when

        // then
        mvc.perform(get("/user/email/"+ email))
            .andExpect(status().is2xxSuccessful())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.userNo").value(3))
            .andExpect(jsonPath("$.name").value("박종훈"))
            .andExpect(jsonPath("$.email").value(email))
            .andExpect(jsonPath("$.picture").value(""));
    }
}