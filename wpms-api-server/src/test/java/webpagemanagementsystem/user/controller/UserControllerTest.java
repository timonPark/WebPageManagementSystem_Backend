package webpagemanagementsystem.user.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import webpagemanagementsystem.common.entity.YNEnum;
import webpagemanagementsystem.common.jwt.AccessTokenProvider;
import webpagemanagementsystem.user.dto.GetUserResDto;
import webpagemanagementsystem.user.dto.LoginReqDto;
import webpagemanagementsystem.user.dto.SignUpReqDto;
import webpagemanagementsystem.user.dto.SignUpResDto;
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

    @MockBean
    private Authentication authentication;

    @MockBean
    private SecurityContext securityContext;

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
                .andExpect(jsonPath("$.data.accessToken").value(accessToken))
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
        when(userService.checkEmail(any(String.class))).thenThrow(new DuplicateEmailException());
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
        when(userService.checkEmail(any(String.class))).thenReturn(true);

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
        // given
        String email = "m05214@naver.com";
        SignUpReqDto reqDto = new SignUpReqDto("박종훈", "m05214@naver.com", "password");
        when(userService.signUp(any(SignUpReqDto.class))).thenThrow(new DuplicateEmailException());
        String content = objectMapper.writeValueAsString(reqDto);
        // when
        mvc.perform(
                        post("/user/signUp")
                                .content(content)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                ).andExpect(status().is4xxClientError())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value("error"))
                .andExpect(jsonPath("$.data").isEmpty())
                .andExpect(jsonPath("$.message").value("이미 가입된 이메일 입니다"));
    }

    @DisplayName("회원가입 성공")
    @Test
    public void test9() throws Exception {
        // given
        String email = "m05214@naver.com";
        SignUpReqDto reqDto = new SignUpReqDto("박종훈", "m05215@naver.com", "password");
        SignUpResDto signUpResDto = SignUpResDto.builder()
                .userNo(1L)
                .name("박종훈")
                .email("m05215@naver.com")
                .build();
        when(userService.signUp(any(SignUpReqDto.class))).thenReturn(signUpResDto);
        String content = objectMapper.writeValueAsString(reqDto);
        // when
        mvc.perform(
                        post("/user/signUp")
                                .content(content)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                ).andExpect(status().is2xxSuccessful())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value("success"))
                .andExpect(jsonPath("$.data.userNo").value(signUpResDto.getUserNo()))
                .andExpect(jsonPath("$.data.name").value(signUpResDto.getName()))
                .andExpect(jsonPath("$.data.email").value(signUpResDto.getEmail()))
                .andExpect(jsonPath("$.message").isEmpty());
    }

    @DisplayName("로그인 실패 - 존재하지 않은 회원")
    @Test
    public void test10() throws Exception {
        // given
        String email = "m05213@naver.com";
        LoginReqDto reqDto = new LoginReqDto(email, "password");
        when(userService.login(any(LoginReqDto.class))).thenThrow(new NonJoinUserException());
        String content = objectMapper.writeValueAsString(reqDto);
        // when
        mvc.perform(
                        post("/user/login")
                                .content(content)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                ).andExpect(status().is4xxClientError())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value("error"))
                .andExpect(jsonPath("$.data").isEmpty())
                .andExpect(jsonPath("$.message").value("미가입 된 회원 입니다."));
    }

    @DisplayName("로그인 실패 - 로그인 인증 실패")
    @Test
    public void test11() throws Exception {
        // given
        String email = "m05214@naver.com";
        LoginReqDto reqDto = new LoginReqDto(email, "password123");
        when(userService.login(any(LoginReqDto.class))).thenThrow(new AuthenticationFailException());
        String content = objectMapper.writeValueAsString(reqDto);
        // when
        mvc.perform(
                        post("/user/login")
                                .content(content)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                ).andExpect(status().is4xxClientError())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value("error"))
                .andExpect(jsonPath("$.data").isEmpty())
                .andExpect(jsonPath("$.message").value("로그인 인증에 실패하였습니다. 인증정보를 다시 확인해주세요"));
    }

    @DisplayName("로그인 성공")
    @Test
    public void test12() throws Exception {
        // given
        String email = "m05214@naver.com";
        String accessToken = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJtMDUyMTRAbmF2ZXIuY29tIiwiYXV0aCI6IlVTRVIiLCJleHAiOjE3MjI1NzkxNzJ9.IyR_tCi7WtBECoPq0ZXKrthriU_kWf8uMwL5B8kUiEeHeEXRUEJb7g7E2zcbBhiUQlHyzaMV06iRe9RnjmibmQ";
        LoginReqDto reqDto = new LoginReqDto(email, "password");
        when(userService.login(any(LoginReqDto.class))).thenReturn(accessToken);
        String content = objectMapper.writeValueAsString(reqDto);
        // when
        mvc.perform(
                        post("/user/login")
                                .content(content)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                ).andExpect(status().is2xxSuccessful())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value("success"))
                .andExpect(jsonPath("$.data.accessToken").value(accessToken))
                .andExpect(jsonPath("$.message").isEmpty());
    }

    @DisplayName("계정 상세조회 실패 - 헤더 토큰 없을 경우")
    @Test
    public void test13() throws Exception {
        // when
        mvc.perform(
            post("/user/getUser")

                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().is4xxClientError());
    }

    @DisplayName("계정 상세조회 성공")
    @Test
    public void test14() throws Exception {
        //given
        String accessToken = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJtMDUyMTQ2QG5hdmVyLmNvbSIsImF1dGgiOiJVU0VSIiwiZXhwIjoxNzIzNTY0NzQ3fQ.1zit3R7wQPmP2qvxykjNiMbxPIxfNKJTsA8NVsraoscjlT3Hac0Ob0HRjGecBBkhLTvY4nHx8JIjLBXbl_V1ug";
        GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_USER");
        User mockUser = new User("m052146@naver.com", "password", Collections.singletonList(authority));

        when(authentication.getPrincipal()).thenReturn(mockUser);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        GetUserResDto userResDto = new GetUserResDto(
            Users.builder()
                .userNo(3L)
                .name("박준영")
                .email("m052146@naver.com")
                .isSocial(YNEnum.N)
                .build()
        );
        when(userService.getUser(any(String.class))).thenReturn(userResDto);

        // when
        mvc.perform(
                get("/user/getUser")
                    .header("Authorization", "Bearer " + accessToken)
                    .contentType(MediaType.APPLICATION_JSON)
            ).andExpect(status().is2xxSuccessful())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.status").value("success"))
            .andExpect(jsonPath("$.data.userNo").value(userResDto.getUserNo()))
            .andExpect(jsonPath("$.data.name").value(userResDto.getName()))
            .andExpect(jsonPath("$.data.email").value(userResDto.getEmail()))
            .andExpect(jsonPath("$.data.isSocial").value(userResDto.getIsSocial().toString()))
            .andExpect(jsonPath("$.data.socialId").isEmpty())
            .andExpect(jsonPath("$.data.socialType").isEmpty())
            .andExpect(jsonPath("$.data.picture").isEmpty())
            .andExpect(jsonPath("$.message").isEmpty());
    }
}