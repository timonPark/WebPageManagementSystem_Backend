package webpagemanagementsystem.user.service;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import webpagemanagementsystem.common.variable.SocialProperties;
import webpagemanagementsystem.user.domain.GoogleSocialInfo;
import webpagemanagementsystem.user.domain.KakaoAccount;
import webpagemanagementsystem.user.domain.KakaoProfile;
import webpagemanagementsystem.user.domain.KakaoSocialInfo;
import webpagemanagementsystem.user.domain.NaverResponse;
import webpagemanagementsystem.user.domain.NaverSocialInfo;
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
  @DisplayName("social getSocialInfo 실패")
  @Test
  void test1(){
    // given
    String accessToken = "OzwbTDNOIls9L4SAISzziPRUwB4_JnL7AAAAAQo9dRsAAAGQcUy12qL4plhSrbcM";
    String platformName = "kakao";
    Class<KakaoSocialInfo>  socialInfoType = KakaoSocialInfo.class;

    // when & then
    Assertions.assertThrows(SocialUnauthorizedException.class, () -> {
      userSocialServiceImpl.getSocialInfo(
          platformName ,
          accessToken,
          socialProperties.platform.get(platformName).getBaseUrl(),
          socialProperties.platform.get(platformName).getPathUrl(),
          socialInfoType
      );
    });
  }

  @DisplayName("social Kakao SocialInfo 성공")
  @Disabled
  @Test
  void test2() throws SocialUnauthorizedException {
    // given
    String accessToken = "mjq_wYzEs5rniQDYnUIcS_nj57MqL__FAAAAAQopyV8AAAGQrE3I8aL4plhSrbcM";
    String platformName = "kakao";
    Class<KakaoSocialInfo>  socialInfoType = KakaoSocialInfo.class;
    KakaoSocialInfo kakaoSocialInfo = new KakaoSocialInfo();
    KakaoProfile kakaoProfile = new KakaoProfile();
    kakaoProfile.setNickname("박종훈");
    KakaoAccount kakaoAccount = new KakaoAccount();
    kakaoAccount.setEmail("m05214@naver.com");
    kakaoAccount.setProfile(kakaoProfile);
    kakaoSocialInfo.setKakaoAccount(kakaoAccount);

    // when
    KakaoSocialInfo resultSocialInfo =userSocialServiceImpl.getSocialInfo(
        platformName,
        accessToken,
        socialProperties.platform.get(platformName).getBaseUrl(),
        socialProperties.platform.get(platformName).getPathUrl(),
        socialInfoType
    );

    // then
    assertThat(kakaoSocialInfo.getKakaoAccount().getEmail()).isEqualTo(resultSocialInfo.getKakaoAccount().getEmail());
    assertThat(kakaoSocialInfo.getKakaoAccount().getProfile().getNickname()).isEqualTo(resultSocialInfo.getKakaoAccount().getProfile().getNickname());
  }

  @DisplayName("social Naver SocialInfo 성공")
  @Disabled
  @Test
  void test3() throws SocialUnauthorizedException {
    // given
    String accessToken = "AAAANgIlTF3ZAnJoMuqorshpbAw5keKfo0x_sDQ-P4o_h6ygyc553695bHQI6QFyCfV6icPEV7H4Fj24lA1yPptI9Bg";
    String platformName = "naver";
    Class<NaverSocialInfo>  socialInfoType = NaverSocialInfo.class;
    NaverSocialInfo naverSocialInfo = new NaverSocialInfo();
    NaverResponse naverResponse = new NaverResponse();
    naverResponse.setId("cbhQt7JpqQkz87OVkH5EkPJZoMoMYKwYcyBGD9J2MC0");
    naverResponse.setProfileImage("https://phinf.pstatic.net/contact/20230306_83/1678064365001C3dar_JPEG/KakaoTalk_Photo_2019-09-11-15-24-01.jpeg");
    naverResponse.setGender("M");
    naverResponse.setEmail("m05214@naver.com");
    naverResponse.setMobile("010-8504-5214");
    naverResponse.setMobileE164("+821085045214");
    naverResponse.setName("박종훈");
    naverSocialInfo.setResponse(naverResponse);
    naverSocialInfo.setResultcode("00");
    naverSocialInfo.setMessage("success");

    // when
    NaverSocialInfo resultSocialInfo =userSocialServiceImpl.getSocialInfo(
        platformName,
        accessToken,
        socialProperties.platform.get(platformName).getBaseUrl(),
        socialProperties.platform.get(platformName).getPathUrl(),
        socialInfoType
    );

    // then
    assertThat(naverSocialInfo).isEqualTo(resultSocialInfo);
  }

  @DisplayName("social Google SocialInfo 성공")
  @Disabled
  @Test
  void test4() throws SocialUnauthorizedException {
    // given
    String accessToken = "ya29.a0AXooCgtrC6cYprCH5k9ct8MOnM25lhCZk4BskP5sXzJyf6_QpPeTPTmOV9t6ZXToscECN4g7xP4L4l19GtG96ME67EDT26V8YL0f8vzxE4L0JlsEcGCpPVocIf5vmDr0gwZtjAywCpEHWdgTlVNRl0GvGY7daALZ8_EaCgYKAfsSARMSFQHGX2MiB1lu_47fteUhBKi2Npn6Ag0170";
    String platformName = "google";
    Class<GoogleSocialInfo>  socialInfoType = GoogleSocialInfo.class;
    GoogleSocialInfo googleSocialInfo = new GoogleSocialInfo();
    googleSocialInfo.setId("112625927003722335487");
    googleSocialInfo.setEmail("kindcorder@gmail.com");
    googleSocialInfo.setVerifiedEmail("true");
    googleSocialInfo.setName("Park jonghoon");
    googleSocialInfo.setGivenName("Park");
    googleSocialInfo.setFamilyName("jonghoon");
    googleSocialInfo.setPicture("https://lh3.googleusercontent.com/a/ACg8ocIlMpTb4DRnXpwM2YhAqAVUS6D1QD5siteHn5IVIy9dPERcew=s96-c");
    googleSocialInfo.setLocale(null);

    // when
    GoogleSocialInfo resultSocialInfo =userSocialServiceImpl.getSocialInfo(
        platformName,
        accessToken,
        socialProperties.platform.get(platformName).getBaseUrl(),
        socialProperties.platform.get(platformName).getPathUrl(),
        socialInfoType
    );

    // then
    assertThat(googleSocialInfo).isEqualTo(resultSocialInfo);
  }
}