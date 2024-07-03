package webpagemanagementsystem.user.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
@Data
public class KakaoAccount {
  @JsonProperty("profile_nickname_needs_agreement")
  Boolean profileNicknameNeedsAgreement;

  @JsonProperty("profile_image_needs_agreement")
  Boolean profileImageNeedsAgreement;

  KakaoProfile profile;

  @JsonProperty("has_email")
  Boolean hasEmail;

  @JsonProperty("email_needs_agreement")
  Boolean emailNeedsAgreement;

  @JsonProperty("is_email_valid")
  Boolean isEmailValid;

  @JsonProperty("is_email_verified")
  Boolean isEmailVerified;

  String email;
}
