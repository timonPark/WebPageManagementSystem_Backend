package webpagemanagementsystem.user.controller;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import webpagemanagementsystem.common.entity.YNEnum;
import webpagemanagementsystem.common.jwt.AccessTokenProvider;
import webpagemanagementsystem.user.dto.SignUpReqDto;
import webpagemanagementsystem.user.dto.SocialRequestDto;
import webpagemanagementsystem.user.entity.SocialType;
import webpagemanagementsystem.user.entity.Users;
import webpagemanagementsystem.user.exception.*;
import webpagemanagementsystem.user.service.UserService;
import webpagemanagementsystem.user.service.UserSocialService;

@ExtendWith(SpringExtension.class)
@WebMvcTest(UserController.class)

class UserControllerTest {

    @Autowired
    MockMvc mvc;

    @MockBean
    UserSocialService userSocialService;

    @MockBean
    UserService userService;

    @MockBean
    private AccessTokenProvider accessTokenProvider;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @BeforeEach
    public void setup() {
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @DisplayName("User API 소셜로그인 실패 - 계정 삭제")
    @Test
    public void test() throws Exception {
        // given
        String socialAccessToken = "AAAANsghieb_FT1ST1MsWN1catbSgmeFtyqk_fYH4nkiW79Hsd9n3za1zc5aA3rmCEupf-cNYDqm7V85X5zO7cFXEyI";
        String socialType = "naver";
        when(userSocialService.socialLoginProgress(socialAccessToken, socialType))
            .thenThrow(new DeleteUserException());

        String content = objectMapper.writeValueAsString(new SocialRequestDto(socialAccessToken));


        // when & then
        mvc.perform(
            post("/user/social/"+ socialType)
                .content(content)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().is4xxClientError())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.status").value("error"))
            .andExpect(jsonPath("$.data").isEmpty())
            .andExpect(jsonPath("$.message").value("해당 계정은 삭제 되었습니다"));
    }

    @DisplayName("User API 소셜로그인 실패 - 휴면 계정")
    @Test
    public void test2() throws Exception {
        // given
        String socialAccessToken = "AAAANsghieb_FT1ST1MsWN1catbSgmeFtyqk_fYH4nkiW79Hsd9n3za1zc5aA3rmCEupf-cNYDqm7V85X5zO7cFXEyI";
        String socialType = "naver";
        when(userSocialService.socialLoginProgress(socialAccessToken, socialType))
            .thenThrow(new NoUseException());

        String content = objectMapper.writeValueAsString(new SocialRequestDto(socialAccessToken));


        // when & then
        mvc.perform(
                post("/user/social/"+ socialType)
                    .content(content)
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().is4xxClientError())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.status").value("error"))
            .andExpect(jsonPath("$.data").isEmpty())
            .andExpect(jsonPath("$.message").value("해당 계정은 휴면 계정입니다"));
    }

    @DisplayName("User API 소셜로그인 실패 - 중복 회원가입")
    @Test
    public void test3() throws Exception {
        // given
        String socialAccessToken = "AAAANsghieb_FT1ST1MsWN1catbSgmeFtyqk_fYH4nkiW79Hsd9n3za1zc5aA3rmCEupf-cNYDqm7V85X5zO7cFXEyI";
        SocialType socialType = SocialType.naver;
        when(userSocialService.socialLoginProgress(socialAccessToken, socialType.name()))
            .thenThrow(
                new DuplicationRegisterException(
                    Users.builder()
                        .isSocial(YNEnum.Y)
                        .socialType(socialType)
                        .build()
                )
            );

        String content = objectMapper.writeValueAsString(new SocialRequestDto(socialAccessToken));


        // when & then
        mvc.perform(
                post("/user/social/"+ socialType)
                    .content(content)
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().is4xxClientError())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.status").value("error"))
            .andExpect(jsonPath("$.data").isEmpty())
            .andExpect(jsonPath("$.message").value("이미 가입되어 있는 계정입니다. 소셜로그인여부: Y, 소셜타입: naver"));
    }

    @DisplayName("User API 소셜로그인 실패 - 소셜 인증실패")
    @Test
    public void test4() throws Exception {
        // given
        String socialAccessToken = "AAAANsghieb_FT1ST1MsWN1catbSgmeFtyqk_fYH4nkiW79Hsd9n3za1zc5aA3rmCEupf-cNYDqm7V85X5zO7cFXEyI";
        String socialType = "naver";
        when(userSocialService.socialLoginProgress(socialAccessToken, socialType))
            .thenThrow(new SocialUnauthorizedException(socialType, socialAccessToken));

        String content = objectMapper.writeValueAsString(new SocialRequestDto(socialAccessToken));


        // when & then
        mvc.perform(
                post("/user/social/"+ socialType)
                    .content(content)
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().is4xxClientError())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.status").value("error"))
            .andExpect(jsonPath("$.data").isEmpty())
            .andExpect(jsonPath("$.message").value("소셜 " +  socialType + " 인증에 실패하였습니다. accessToken: " + socialAccessToken));
    }

    @DisplayName("User API 소셜로그인 성공")
    @Test
    public void test5() throws Exception {
        // given
        String socialAccessToken = "AAAANnUbYOU2X9Zg22LWLCXv5rhSUo04vwEw2zQyRbPr3IUCrNXJ452XHevy3f23KowPaO9CgN_0oorGA_kusB06gcc";
        String socialType = "naver";
        String accessToken = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJtMDUyMTRAbmF2ZXIuY29tIiwiYXV0aCI6IlVTRVIiLCJleHAiOjE3MjE0ODEzNzR9.HltI_le2vbsq0QbajWV_o8W4mmuwo7SZGYzI4iNrOqWLYVUPtVl3phmo0i1R7aracGxZLgEddB43fMtWpI2yuA";
        given(userSocialService.socialLoginProgress(socialAccessToken, socialType)).willReturn(accessToken);
        String content = objectMapper.writeValueAsString(new SocialRequestDto(socialAccessToken));


        // when & then
        mvc.perform(
                post("/user/social/"+ socialType)
                    .content(content)
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().is2xxSuccessful())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.status").value("success"))
            .andExpect(jsonPath("$.data.accesstoken").value(accessToken))
            .andExpect(jsonPath("$.message").isEmpty());
    }

    /**
     * {
     * "status": "error",
     * "data": null,
     * "message": "이미 가입된 이메일 입니다"
     * }
     * @throws Exception
     */


    @DisplayName("중복이메일 검사 - 이미 존재")
    @Test
    public void test6() throws Exception {
        String email = "m05214@naver.com";
        // given
        when(userService.checkEmail(email)).thenThrow(new DuplicateEmailException());
        // when
        mvc.perform(
                get("/user/checkEmail/"+email)
        ).andExpect(status().is4xxClientError())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value("error"))
                .andExpect(jsonPath("$.data").isEmpty())
                .andExpect(jsonPath("$.message").value("이미 가입된 이메일 입니다"));
    }

    /**
     * {
     * "status": "success",
     * "data": true,
     * "message": null
     * }
     * @throws Exception
     */

    @DisplayName("중복이메일 검사 - 중복없음")
    @Test
    public void test7() throws Exception {
        String email = "m05217@naver.com";
        // given
        when(userService.checkEmail(email)).thenReturn(true);

        // when & then
        mvc.perform(get("/user/checkEmail/"+email))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value("success"))
                .andExpect(jsonPath("$.data").value(true))
                .andExpect(jsonPath("$.message").isEmpty());
    }

    @DisplayName("회원가입 실패 - 이메일 중복")
    @Test
    public void test8() throws Exception {
        String email = "m05217@naver.com";
        // given
        SignUpReqDto reqDto = new SignUpReqDto("박종훈", "m05214@naver.com", "password");
        when(userService.checkEmail(email)).thenThrow(new DuplicateEmailException());
        when(userService.signUp(reqDto)).thenThrow(new DuplicateEmailException());

        String content = objectMapper.writeValueAsString(reqDto);

        // when & then
        mvc.perform(
                post("/user/signUp")
                    .content(content)
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is4xxClientError())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value("error"))
                .andExpect(jsonPath("$.data").isEmpty())
                .andExpect(jsonPath("$.message").value("이미 가입된 이메일 입니다"));

    }

    @DisplayName("회원가입 성공")
    @Test
    public void test9() throws Exception {

    }
}