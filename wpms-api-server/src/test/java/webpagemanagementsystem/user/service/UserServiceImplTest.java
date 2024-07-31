package webpagemanagementsystem.user.service;

import java.util.*;

import net.wpms.entity.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.crypto.password.PasswordEncoder;
import webpagemanagementsystem.common.entity.IsUseEnum;
import webpagemanagementsystem.common.entity.YNEnum;
import webpagemanagementsystem.common.jwt.AccessTokenProvider;
import webpagemanagementsystem.user.dto.FindByEmailResponse;
import webpagemanagementsystem.user.dto.LoginReqDto;
import webpagemanagementsystem.user.dto.SignUpReqDto;
import webpagemanagementsystem.user.dto.SignUpResDto;
import webpagemanagementsystem.user.entity.SocialType;
import webpagemanagementsystem.user.entity.Users;
import webpagemanagementsystem.user.exception.*;
import webpagemanagementsystem.user.repository.UsersRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.assertj.core.api.Assertions.*;


@SpringBootTest
class UserServiceImplTest {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManagerBuilder authenticationManagerBuilder;

    @Autowired
    private AccessTokenProvider accessTokenProvider;


    @MockBean(name = "usersRepository")
    private UsersRepository usersRepository;

    @DisplayName("UsersService email로_Users_객체조회_실패")
    @Test
    void test(){
        // given
        String email = "m11111@naver.com";

        given(usersRepository.findByEmailAndIsUse(email, IsUseEnum.U)).willThrow(new NoSuchElementException());

        // when & then
        Users result = userService.findByEmailAndIsUseY(email);

        assertThat(result).isNull();
    }

    @DisplayName("UsersService email로_Users_객체조회_성공")
    @Test
    void test2(){
        // given
        String email = "m11111@naver.com";
        Users user = Users.builder()
                .name("박종훈")
                .email(email)
                .password(null)
                .isSocial(YNEnum.Y)
                .socialId("18346737826")
                .socialType(SocialType.kakao)
                .picture(null)
                .isUse(IsUseEnum.U)
                .build();
        List<Users> list = new ArrayList<>();
        list.add(user);
        given(usersRepository.findByEmailAndIsUse(email, IsUseEnum.U)).willReturn(list);

        // when
        Users resultUser = userService.findByEmailAndIsUseY(email);

        // then
        assertThat(resultUser).isEqualTo(user);
    }

    @DisplayName("convertUsersToFindByEmailResponse의 파라미터가 null 인 경우")
    @Test
    void test3(){


        // when
        FindByEmailResponse findByEmailResponse = userService.convertUsersToFindByEmailResponse(null);

        // then
        assertThat(findByEmailResponse).isEqualTo(null);
    }

    @DisplayName("convertUsersToFindByEmailResponse의 파라미터가 null이 아닌 경우")
    @Test
    void test4(){
        // given
        String email = "m11111@naver.com";
        Users user = Users.builder()
            .name("박종훈")
            .email(email)
            .password(null)
            .isSocial(YNEnum.Y)
            .socialId("18346737826")
            .socialType(SocialType.kakao)
            .picture(null)
            .isUse(IsUseEnum.U)
            .build();
        List<Users> list = new ArrayList<>();
        list.add(user);
        given(usersRepository.findByEmailAndIsUse(email, IsUseEnum.U)).willReturn(list);
        FindByEmailResponse findByEmailResponse = new FindByEmailResponse(user);

        // when
        Users resultUser = userService.findByEmailAndIsUseY(email);
        FindByEmailResponse resultFindByEmailResponse = userService.convertUsersToFindByEmailResponse(resultUser);

        // then
        assertThat(resultFindByEmailResponse).isEqualTo(findByEmailResponse);
    }



    @DisplayName("preventDuplicationRegist Exception 발생 테스트 1")
    @Test
    void test10() {
        // given
        Users user = Users.builder()
            .userNo(15L)
            .name("홍길동")
            .email("hong@naver.com")
            .isSocial(YNEnum.Y)
            .socialId("22222223")
            .socialType(SocialType.kakao)
            .picture(null)
            .isUse(IsUseEnum.U)
            .build();
        Users expectResultUser = Users.builder()
            .userNo(15L)
            .name("홍길동")
            .email("hong@naver.com")
            .isSocial(YNEnum.N)
            .socialId(null)
            .socialType(null)
            .picture(null)
            .isUse(IsUseEnum.U)
            .build();



        // when && then
        Assertions.assertThrows(DuplicationRegisterException.class, () -> {
            userService.preventDuplicationRegist(user, expectResultUser);
        });


    }

    @DisplayName("preventDuplicationRegist Exception 발생 테스트 2")
    @Test
    void test11() {
        // given
        Users user = Users.builder()
            .userNo(15L)
            .name("홍길동")
            .email("hong@naver.com")
            .isSocial(YNEnum.Y)
            .socialId("22222223")
            .socialType(SocialType.kakao)
            .picture(null)
            .isUse(IsUseEnum.U)
            .build();
        Users expectResultUser = Users.builder()
            .userNo(15L)
            .name("홍길동")
            .email("hong@naver.com")
            .isSocial(YNEnum.Y)
            .socialId("22222223")
            .socialType(SocialType.naver)
            .picture(null)
            .isUse(IsUseEnum.U)
            .build();

        // when && then
        Assertions.assertThrows(DuplicationRegisterException.class, () -> {
            userService.preventDuplicationRegist(user, expectResultUser);
        });
    }

    @DisplayName("preventDuplicationRegist 성공 테스트")
    @Test
    void test12() throws DuplicationRegisterException {
        // given
        Users user = Users.builder()
            .userNo(15L)
            .name("홍길동")
            .email("hong@naver.com")
            .isSocial(YNEnum.Y)
            .socialId("22222223")
            .socialType(SocialType.naver)
            .picture(null)
            .isUse(IsUseEnum.U)
            .build();
        Users expectResultUser = Users.builder()
            .userNo(15L)
            .name("홍길동")
            .email("hong@naver.com")
            .isSocial(YNEnum.Y)
            .socialId("22222223")
            .socialType(SocialType.naver)
            .picture(null)
            .isUse(IsUseEnum.U)
            .build();

        userService.preventDuplicationRegist(user, expectResultUser);
    }

    @DisplayName("validateUserIsUse 중 IsUseEnum.N인 경우 테스트")
    @Test
    void test13() {
        // given
        Users expectResultUser = Users.builder()
            .userNo(15L)
            .name("홍길동")
            .email("hong@naver.com")
            .isSocial(YNEnum.Y)
            .socialId("22222223")
            .socialType(SocialType.naver)
            .picture(null)
            .isUse(IsUseEnum.N)
            .build();

        // when & then
        Assertions.assertThrows(NoUseException.class, () -> {
            userService.validateUserIsUse(expectResultUser);
        });
    }

    @DisplayName("validateUserIsUse 중 IsUseEnum.D인 경우 테스트")
    @Test
    void test14() {
        // given
        Users expectResultUser = Users.builder()
            .userNo(15L)
            .name("홍길동")
            .email("hong@naver.com")
            .isSocial(YNEnum.Y)
            .socialId("22222223")
            .socialType(SocialType.naver)
            .picture(null)
            .isUse(IsUseEnum.D)
            .build();

        // when & then
        Assertions.assertThrows(DeleteUserException.class, () -> {
            userService.validateUserIsUse(expectResultUser);
        });
    }

    @DisplayName("이메일 중복여부 검사 - 중복")
    @Test
    void test15() throws DuplicateEmailException {
        // given
        String email = "m05214@naver.com";
        given(userService.findByEmailAndIsUseY(email)).willThrow(new NoSuchElementException());

        // when & then
        Assertions.assertThrows(DuplicateEmailException.class, () -> {
            userService.checkEmail(email);
        });
    }

    @DisplayName("이메일 중복여부 검사 - 중복X")
    @Test
    void test16() throws DuplicateEmailException {
        // given
        String email = "m05214@naver.com";
        Users user = Users.builder()
                .userNo(1L)
                .email(email)
                .password("password")
                .isSocial(YNEnum.Y)
                .isUse(IsUseEnum.U)
                .build();
        given(usersRepository.findByEmailAndIsUse(email,IsUseEnum.U)).willReturn(Collections.singletonList(user));

        // when
        boolean result = userService.checkEmail(email);

        // then
        Assertions.assertTrue(result);


    }

    @DisplayName("회원가입 실패 - 유효성검사(이름, 이메일, 패스워드)")
    @Test
    void test17() {

    }

    @DisplayName("회원가입 성공")
    @Test
    void test18() {

        // given
        String email = "m05214@naver.com";
        Users user = Users.builder()
                .email(email)
                .name("박종훈")
                .password("password")
                .isSocial(YNEnum.Y)
                .isUse(IsUseEnum.U)
                .build();
        Users resultUser = Users.builder()
                .userNo(2L)
                .name("박종훈")
                .email(email)
                .password("$2a$10$OJmFXX38e0NsM9gx/8uefuNQVNrXO/sxsIIueij2Ql3/s03y3/YSi")
                .isSocial(YNEnum.Y)
                .isUse(IsUseEnum.U)
                .build();
        given(usersRepository.save(any(Users.class))).willReturn(resultUser);
        // when
        SignUpResDto testResult = userService.signUp(new SignUpReqDto(user.getName(), user.getEmail(), user.getPassword()));

        // then
        assertThat(testResult).isEqualTo(SignUpResDto.builder()
                .userNo(resultUser.getUserNo())
                .name(resultUser.getName())
                .email(resultUser.getEmail())
                .build());
    }

    @DisplayName("로그인 실패 - 존재하지 않는 회원")
    @Test
    void test19() {
        // given
        String email = "aaaa@naver.com";
        given(userService.findByEmailAndIsUseY(email)).willThrow(new NoSuchElementException());

        // when & then
        Assertions.assertThrows(NonJoinUserException.class, () -> {
            userService.login(new LoginReqDto(email, "password"));
        });
    }

    @DisplayName("로그인 실패 - 아이디 패스워드 불일치")
    @Test
    void test20() {
        String email = "m05214@naver.com";
        LoginReqDto loginReqDto = new LoginReqDto(email, "pass");
        Users resultUser = Users.builder()
                .userNo(2L)
                .name("박종훈")
                .email(email)
                .password("$2a$10$OJmFXX38e0NsM9gx/8uefuNQVNrXO/sxsIIueij2Ql3/s03y3/YSi")
                .isSocial(YNEnum.Y)
                .isUse(IsUseEnum.U)
                .build();
        given(usersRepository.findByEmailAndIsUse(any(String.class), any(IsUseEnum.class))).willReturn(Collections.singletonList(resultUser));

        Assertions.assertThrows(AuthenticationFailException.class, () -> {
            userService.login(loginReqDto);
        });
    }

    @DisplayName("로그인 성공")
    @Test
    void test21() throws AuthenticationFailException, NonJoinUserException {
        String email = "m05214@naver.com";
        LoginReqDto loginReqDto = new LoginReqDto(email, "password");
        Users resultUser = Users.builder()
                .userNo(2L)
                .name("박종훈")
                .email(email)
                .password("$2a$10$OJmFXX38e0NsM9gx/8uefuNQVNrXO/sxsIIueij2Ql3/s03y3/YSi")
                .isSocial(YNEnum.N)
                .isUse(IsUseEnum.U)
                .build();
        given(usersRepository.findByEmailAndIsUse(any(String.class), any(IsUseEnum.class))).willReturn(Collections.singletonList(resultUser));

        String accessToken = userService.login(loginReqDto);

        assertThat(accessToken).isNotEmpty();
        assertThat(accessToken).isInstanceOf(String.class);
    }
}