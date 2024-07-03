package webpagemanagementsystem.user.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import webpagemanagementsystem.common.entity.IsUseEnum;
import webpagemanagementsystem.user.entity.SocialType;
import webpagemanagementsystem.user.entity.Users;

@SpringBootTest
class KakaoSocialInfoTest {

  @DisplayName("convertKakaoSocialInfoToUsers 성공")
  @Test
  public void test(){

    // given
    KakaoProfile kakaoProfile = new KakaoProfile();
    kakaoProfile.setNickname("박종훈");
    kakaoProfile.setThumbnailImageUrl("http://k.kakaocdn.net/dn/RyqZD/btsId5KXwuM/ktArUkb8CML24wnCPhx6xk/img_110x110.jpg");
    kakaoProfile.setProfileImageUrl("http://k.kakaocdn.net/dn/RyqZD/btsId5KXwuM/ktArUkb8CML24wnCPhx6xk/img_640x640.jpg");
    kakaoProfile.setIsDefaultImage(false);
    kakaoProfile.setIsDefaultNickname(false);

    KakaoAccount kakaoAccount = new KakaoAccount();
    kakaoAccount.setProfileNicknameNeedsAgreement(false);
    kakaoAccount.setProfileImageNeedsAgreement(false);
    kakaoAccount.setProfile(kakaoProfile);
    kakaoAccount.setHasEmail(true);
    kakaoAccount.setEmailNeedsAgreement(false);
    kakaoAccount.setIsEmailValid(true);
    kakaoAccount.setIsEmailVerified(true);
    kakaoAccount.setEmail("m05214@naver.com");

    KaKaoProperties kaKaoProperties = new KaKaoProperties();
    kaKaoProperties.setNickname("박종훈");
    kaKaoProperties.setProfileImage("http://k.kakaocdn.net/dn/RyqZD/btsId5KXwuM/ktArUkb8CML24wnCPhx6xk/img_640x640.jpg");
    kaKaoProperties.setThumbnailImage("http://k.kakaocdn.net/dn/RyqZD/btsId5KXwuM/ktArUkb8CML24wnCPhx6xk/img_110x110.jpg");

    KakaoSocialInfo kakaoSocialInfo = new KakaoSocialInfo();
    kakaoSocialInfo.setId(3538716799L);
    kakaoSocialInfo.setConnectedAt(LocalDateTime.of(2024, 6, 17, 19, 4, 9));
    kakaoSocialInfo.setKakaoAccount(kakaoAccount);
    kakaoSocialInfo.setProperties(kaKaoProperties);

    // when
    Users resultUser = kakaoSocialInfo.convertKakaoSocialInfoToUsers();

    // then
    assertThat(resultUser.getEmail()).isEqualTo(kakaoSocialInfo.getKakaoAccount().getEmail());
    assertThat(resultUser.getSocialId()).isEqualTo(kakaoSocialInfo.getId().toString());
    assertThat(resultUser.getSocialType()).isEqualTo(SocialType.kakao);
  }
}