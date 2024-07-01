package webpagemanagementsystem.user.service;

import java.util.ArrayList;
import java.util.Arrays;
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
    public void test(){
        // given
        String email = "m11111@naver.com";

        given(usersRepository.findByEmailAndIsUse(email, IsUseEnum.U)).willThrow(new NoSuchElementException());

        // when & then
        Users result = userService.findByEmail(email);

        assertThat(result).isNull();
    }

    @DisplayName("UsersService email로_Users_객체조회_성공")
    @Test
    public void test2(){
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
        Users resultUser = userService.findByEmail(email);

        // then
        assertThat(resultUser).isEqualTo(user);
    }

    @DisplayName("convertUsersToFindByEmailResponse의 파라미터가 null 인 경우")
    @Test
    public void test3(){


        // when
        FindByEmailResponse findByEmailResponse = userService.convertUsersToFindByEmailResponse(null);

        // then
        assertThat(findByEmailResponse).isEqualTo(null);
    }

    @DisplayName("convertUsersToFindByEmailResponse의 파라미터가 null이 아닌 경우")
    @Test
    public void test4(){
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
        Users resultUser = userService.findByEmail(email);
        FindByEmailResponse resultFindByEmailResponse = userService.convertUsersToFindByEmailResponse(resultUser);

        // then
        assertThat(resultFindByEmailResponse).isEqualTo(findByEmailResponse);
    }
}