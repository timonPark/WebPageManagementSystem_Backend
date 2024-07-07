package webpagemanagementsystem.user.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import webpagemanagementsystem.common.entity.IsUseEnum;
import webpagemanagementsystem.common.variable.SocialProperties;
import webpagemanagementsystem.user.domain.KakaoSocialInfo;
import webpagemanagementsystem.user.entity.Users;
import webpagemanagementsystem.user.exception.SocialUnauthorizedException;
import webpagemanagementsystem.user.repository.UsersRepository;

@SpringBootTest
class KakaoUserServiceImplTest {

  @Autowired
  private SocialProperties socialProperties;

  @Autowired
  private UserSocialService userSocialService;

  @Autowired
  private UserService userService;

  @MockBean(name = "usersRepository")
  private UsersRepository usersRepository;
  @DisplayName("social KAKAO getSocialInfo 실패")
  @Test
  void test5(){
    // given
    String accessToken = "OzwbTDNOIls9L4SAISzziPRUwB4_JnL7AAAAAQo9dRsAAAGQcUy12qL4plhSrbcM";
    String platformName = "kakao";

    // when & then
    Assertions.assertThrows(SocialUnauthorizedException.class, () -> {
      userSocialService.getSocialInfo(
          platformName ,
          accessToken,
          socialProperties.platform.get(platformName).getBaseUrl(),
          socialProperties.platform.get(platformName).getPathUrl()
      );
    });
  }

  @DisplayName("social KAKAO getSocialInfo 성공")
  @Disabled
  @Test
  void test6() throws SocialUnauthorizedException {
    // given
    String accessToken = "qHB9Xxbxr4yEoZtZZjJsSP3KDkdiCRXiAAAAAQopyNkAAAGQcy-ecqL4plhSrbcM";
    String platformName = "kakao";

    // when
    Map<String, Object> resultMap = userSocialService.getSocialInfo(
        platformName,
        accessToken,
        socialProperties.platform.get(platformName).getBaseUrl(),
        socialProperties.platform.get(platformName).getPathUrl()
    );

    final ObjectMapper mapper = new ObjectMapper();
    final KakaoSocialInfo kakaoSocialInfo = mapper.convertValue(resultMap, KakaoSocialInfo.class);
  }

  @DisplayName("convertHashMapToGeneric 성공")
  @Disabled
  @Test
  void test7() throws SocialUnauthorizedException {
    // given
    String accessToken = "PzkY9rl8SvhXeTRH6wcdbmtMXMyWgAhQAAAAAQorDKgAAAGQc_gS1KL4plhSrbcM";
    String platformName = "kakao";
    Map<String, Object> socialInfoMap = userSocialService.getSocialInfo(
        platformName,
        accessToken,
        socialProperties.platform.get(platformName).getBaseUrl(),
        socialProperties.platform.get(platformName).getPathUrl()
    );

    // when
    KakaoSocialInfo result = userSocialService.convertHashMapToGeneric(socialInfoMap, KakaoSocialInfo.class);

    System.out.println(result);
    // then
    assertThat(result.getKakaoAccount().getEmail()).isEqualTo("m05214@naver.com");
    assertThat(result.getId()).isEqualTo(3538716799L);


  }

  @DisplayName("accessToken을 받아서 kakaoInfo를 받아서 Users 객체로 치환 후 Save 성공")
  @Disabled
  @Test
  void test8() throws SocialUnauthorizedException {
    // given
    String accessToken = "MB-D8MTH01MDSmEu5DStdW4ctYwUMv4dAAAAAQo9dVwAAAGQd2QewqL4plhSrbcM";
    String platformName = "kakao";
    Map<String, Object> socialInfoMap = userSocialService.getSocialInfo(
        platformName,
        accessToken,
        socialProperties.platform.get(platformName).getBaseUrl(),
        socialProperties.platform.get(platformName).getPathUrl()
    );

    KakaoSocialInfo kakaoSocialInfo = userSocialService.convertHashMapToGeneric(socialInfoMap, KakaoSocialInfo.class);
    Users user = kakaoSocialInfo.convertKakaoSocialInfoToUsers();
    Users expectResultUser = Users.builder()
        .userNo(15L)
        .name(user.getName())
        .email(user.getEmail())
        .isSocial(user.getIsSocial())
        .socialId(user.getSocialId())
        .socialType(user.getSocialType())
        .picture(user.getPicture())
        .isUse(user.getIsUse())
        .build();
    given(usersRepository.save(user)).willReturn(expectResultUser);
    given(usersRepository.findByEmailAndIsUse(user.getEmail(), IsUseEnum.U))
        .willReturn(Collections.singletonList(expectResultUser));

    // when
    Users resultSaveUser =  usersRepository.save(user);
    Users findByEmailUser = userService.findByEmailAndIsUseY(user.getEmail());

    assertThat(resultSaveUser).isEqualTo(findByEmailUser);
  }

  @DisplayName("kakao Users 객체를 가지고 accessToken 생성 테스트")
  @Disabled
  @Test
  void test9() throws SocialUnauthorizedException {
    // given
    String platformAccessToken = "iYcyRoKcy8-p8TYOH7kDux1R_ACH1wwfAAAAAQopyV4AAAGQfFlOw6L4plhSrbcM";
    String platformName = "kakao";
    Map<String, Object> socialInfoMap = userSocialService.getSocialInfo(
        platformName,
        platformAccessToken,
        socialProperties.platform.get(platformName).getBaseUrl(),
        socialProperties.platform.get(platformName).getPathUrl()
    );

    KakaoSocialInfo kakaoSocialInfo = userSocialService.convertHashMapToGeneric(socialInfoMap, KakaoSocialInfo.class);
    Users user = kakaoSocialInfo.convertKakaoSocialInfoToUsers();
    Users expectResultUser = Users.builder()
        .userNo(15L)
        .name(user.getName())
        .email(user.getEmail())
        .isSocial(user.getIsSocial())
        .socialId(user.getSocialId())
        .socialType(user.getSocialType())
        .picture(user.getPicture())
        .isUse(user.getIsUse())
        .build();

    given(usersRepository.findByEmailAndIsUse(user.getEmail(), IsUseEnum.U)).willReturn(Arrays.asList(expectResultUser));

    // when
    String accessToken = userSocialService.socialAuthenticate(expectResultUser);

    // then
    assertThat(accessToken).isNotNull();
    System.out.println(accessToken);
  }
}