package webpagemanagementsystem.user.service;

import static org.junit.jupiter.api.Assertions.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import webpagemanagementsystem.common.variable.SocialProperties;
import webpagemanagementsystem.user.domain.KakaoSocialInfo;
import webpagemanagementsystem.user.exception.SocialUnauthorizedException;
import webpagemanagementsystem.user.repository.UsersRepository;

@SpringBootTest
class UserSocialServiceImplTest {

  @Autowired
  private SocialProperties socialProperties;

  @Autowired
  private UserSocialServiceImpl userSocialServiceImpl;

  @MockBean(name = "usersRepository")
  private UsersRepository usersRepository;
  @DisplayName("social KAKAO getSocialInfo 실패")
  @Test
  void test1(){
    // given
    String accessToken = "OzwbTDNOIls9L4SAISzziPRUwB4_JnL7AAAAAQo9dRsAAAGQcUy12qL4plhSrbcM";
    String platformName = "kakao";

    // when & then
    Assertions.assertThrows(SocialUnauthorizedException.class, () -> {
      userSocialServiceImpl.getSocialInfo(
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
  void test2() throws SocialUnauthorizedException {
    // given
    String accessToken = "qHB9Xxbxr4yEoZtZZjJsSP3KDkdiCRXiAAAAAQopyNkAAAGQcy-ecqL4plhSrbcM";
    String platformName = "kakao";

    // when
    Map<String, Object> resultMap = userSocialServiceImpl.getSocialInfo(
        platformName,
        accessToken,
        socialProperties.platform.get(platformName).getBaseUrl(),
        socialProperties.platform.get(platformName).getPathUrl()
    );

    final ObjectMapper mapper = new ObjectMapper();
    final KakaoSocialInfo kakaoSocialInfo = mapper.convertValue(resultMap, KakaoSocialInfo.class);
  }

  @DisplayName("social KAKAO getSocialInfo 실패")
  @Test
  void test5(){
    // given
    String accessToken = "OzwbTDNOIls9L4SAISzziPRUwB4_JnL7AAAAAQo9dRsAAAGQcUy12qL4plhSrbcM";
    String platformName = "kakao";

    // when & then
    Assertions.assertThrows(SocialUnauthorizedException.class, () -> {
      userSocialServiceImpl.getSocialInfo(
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
    Map<String, Object> resultMap = userSocialServiceImpl.getSocialInfo(
        platformName,
        accessToken,
        socialProperties.platform.get(platformName).getBaseUrl(),
        socialProperties.platform.get(platformName).getPathUrl()
    );

    final ObjectMapper mapper = new ObjectMapper();
    final KakaoSocialInfo kakaoSocialInfo = mapper.convertValue(resultMap, KakaoSocialInfo.class);
  }

}