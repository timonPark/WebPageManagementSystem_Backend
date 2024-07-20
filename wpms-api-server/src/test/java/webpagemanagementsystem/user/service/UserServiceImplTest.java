package webpagemanagementsystem.user.service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import webpagemanagementsystem.common.entity.IsUseEnum;
import webpagemanagementsystem.common.entity.YNEnum;
import webpagemanagementsystem.user.dto.FindByEmailResponse;
import webpagemanagementsystem.user.entity.SocialType;
import webpagemanagementsystem.user.entity.Users;
import webpagemanagementsystem.user.exception.DeleteUserException;
import webpagemanagementsystem.user.exception.DuplicationRegisterException;
import webpagemanagementsystem.user.exception.NoUseException;
import webpagemanagementsystem.user.repository.UsersRepository;

import static org.mockito.BDDMockito.given;
import static org.assertj.core.api.Assertions.*;


@SpringBootTest
class UserServiceImplTest {

    @Autowired
    private UserService userService;

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
}