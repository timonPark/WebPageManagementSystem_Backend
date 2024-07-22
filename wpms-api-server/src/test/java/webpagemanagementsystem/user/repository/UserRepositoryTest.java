package webpagemanagementsystem.user.repository;


import static org.assertj.core.api.Assertions.*;

import java.util.NoSuchElementException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import webpagemanagementsystem.common.entity.IsUseEnum;
import webpagemanagementsystem.common.entity.YNEnum;
import webpagemanagementsystem.user.entity.SocialType;
import webpagemanagementsystem.user.entity.Users;

@SpringBootTest
class UserRepositoryTest {

    @Autowired
    UsersRepository userRepository;

    @Transactional
    @DisplayName("Users_객체_저장_성공 - 회원가입")
    @Test
    public void test1() {
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

        // when
        Users resultUser = userRepository.save(user);

        // then
        assertThat(user).isEqualTo(resultUser);
        assertThat(resultUser.getCreatedAt()).isNotNull();
    }

    @DisplayName("email로_Users_객체조회_실패")
    @Test
    public void test2(){
        // given
        String email = "m11111@naver.com";

        // when & then
        Assertions.assertThrows(NoSuchElementException.class, () -> {
            userRepository.findByEmailAndIsUse(email, IsUseEnum.U).stream().findFirst().orElseThrow();
        });
    }

    @Transactional
    @DisplayName("email로_Users_객체조회_성공")
    @Test
    public void test3(){
        // given
        String email = "m11111@naver.com";
        Users user = userRepository.save(Users.builder()
                .name("박종훈")
                .email(email)
                .password(null)
                .isSocial(YNEnum.Y)
                .socialId("18346737826")
                .socialType(SocialType.kakao)
                .picture(null)
                .isUse(IsUseEnum.U)
                .build());

        // when
        Users resultUser = userRepository.findByEmailAndIsUse(email, IsUseEnum.U).stream().findFirst().orElseThrow();

        // then
        assertThat(user.getUserNo()).isEqualTo(resultUser.getUserNo());
    }

}