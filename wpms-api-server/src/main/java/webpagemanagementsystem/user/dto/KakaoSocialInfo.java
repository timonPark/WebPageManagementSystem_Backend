package webpagemanagementsystem.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Date;
import lombok.Data;

@Data
public class KakaoSocialInfo {
  Long id;

  @JsonProperty("connected_at")
  Date connectedAt;

  @JsonProperty("kakao_account")
  KakaoAccount kakaoAccount;

  Properties properties;


  @Data
  static class KakaoAccount {

    @JsonProperty("profile_nickname_needs_agreement")
    Boolean profileNicknameNeedsAgreement;

    @JsonProperty("profile_image_needs_agreement")
    Boolean profileImageNeedsAgreement;

    Profile profile;

    @JsonProperty("has_email")
    Boolean hasEmail;

    @JsonProperty("email_needs_agreement")
    Boolean emailNeedsAgreement;

    @JsonProperty("is_email_valid")
    Boolean isEmailValid;

    @JsonProperty("is_email_verified")
    Boolean isEmailVerified;

    String email;
    @Data
    static class Profile {

      String nickname;

      @JsonProperty("thumbnail_image_url")
      String thumbnailImageUrl;

      @JsonProperty("profile_image_url")
      String profileImageUrl;

      @JsonProperty("is_default_image")
      Boolean isDefaultImage;

      @JsonProperty("is_default_nickname")
      Boolean isDefaultNickname;
    }
  }

  @Data
  class Properties {

    String nickname;

    @JsonProperty("profile_image")
    String profileImage;

    @JsonProperty("thumbnail_image")
    String thumbnailImage;
  }
}


