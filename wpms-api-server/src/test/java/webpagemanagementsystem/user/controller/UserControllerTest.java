package webpagemanagementsystem.user.controller;


import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
import webpagemanagementsystem.user.exception.DeleteUserException;
import webpagemanagementsystem.user.exception.SocialUnauthorizedException;
import webpagemanagementsystem.user.service.UserService;
import webpagemanagementsystem.user.service.UserSocialService;

@ExtendWith(SpringExtension.class)
@WebMvcTest(UserController.class)

class UserControllerTest {

    @Autowired
    MockMvc mvc;

    @MockBean
    UserSocialService userSocialService;

    @DisplayName("User API 소셜로그인 실패 - 계정 삭제")
    @Test
    public void test() throws Exception {
//        // given
//        String socialAccessToken = "AAAANsghieb_FT1ST1MsWN1catbSgmeFtyqk_fYH4nkiW79Hsd9n3za1zc5aA3rmCEupf-cNYDqm7V85X5zO7cFXEyI";
//        String socialType = "naver";
//        String serverAccessToken = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJtMDUyMTRAbmF2ZXIuY29tIiwiYXV0aCI6IlVTRVIiLCJleHAiOjE3MjExMzgyMTZ9.rnGotMlOJW75qeS5AMxn-wzRwgSZ4_Iq72ysLk-oNu4bB9o6aF6g4YFiyGHP1LUlhziPNnVyN45oZj6YcUFnTQ";
//        given(userSocialService.socialLoginProgress(socialAccessToken, socialType))
//            .willThrow(DeleteUserException.class);
//
//        // when & then
//        mvc.perform(post("/user/social/"+ socialType))
//            .andExpect(status().is4xxClientError())
//            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//            .andExpect(jsonPath("$.status").value("error"))
//            .andExpect(jsonPath("$.data").value(null))
//            .andExpect(jsonPath("$.message").value("이미 가입되어 있는 계정입니다. 소셜로그인여부: Y 소셜타입: naver"));
    }

    @DisplayName("User API 소셜로그인 실패 - 휴면 계정")
    @Test
    public void test2() throws Exception {

    }

    @DisplayName("User API 소셜로그인 실패 - 중복 회원가입")
    @Test
    public void test3() throws Exception {

    }

    @DisplayName("User API 소셜로그인 실패 - 소셜 인증실패")
    @Test
    public void test4() throws Exception {

    }

    @DisplayName("User API 소셜로그인 성공")
    @Test
    public void test5() throws Exception {

    }

//    @DisplayName("User API 중 이메일로 계정 검색 실패")
//    @Test
//    public void test2() throws Exception {
//        // given
//        String email = "m11111@naver.com";
//        Users user = Users.builder()
//            .userNo(3L)
//            .name("박종훈")
//            .email(email)
//            .password(null)
//            .isSocial(YNEnum.Y)
//            .socialId("18346737826")
//            .socialType(SocialType.kakao)
//            .picture("")
//            .isUse(IsUseEnum.U)
//            .build();
//        FindByEmailResponse findByEmailResponse = new FindByEmailResponse(user);
//        given(userService.findByEmailAndIsUseY(email)).willReturn(user);
//        given(userService.convertUsersToFindByEmailResponse(user)).willReturn(findByEmailResponse);
//
//        // when
//
//        // then
//        mvc.perform(get("/user/email/"+ email))
//            .andExpect(status().is2xxSuccessful())
//            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//            .andExpect(jsonPath("$.userNo").value(3))
//            .andExpect(jsonPath("$.name").value("박종훈"))
//            .andExpect(jsonPath("$.email").value(email))
//            .andExpect(jsonPath("$.picture").value(""));
//    }
}