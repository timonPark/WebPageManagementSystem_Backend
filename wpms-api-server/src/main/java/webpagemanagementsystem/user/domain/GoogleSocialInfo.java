package webpagemanagementsystem.user.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import webpagemanagementsystem.common.entity.IsUseEnum;
import webpagemanagementsystem.common.entity.YNEnum;
import webpagemanagementsystem.user.entity.SocialType;
import webpagemanagementsystem.user.entity.Users;

@Data
public class GoogleSocialInfo implements SocialInfo{

  String id;

  String email;

  @JsonProperty("verified_email")
  String verifiedEmail;

  String name;

  @JsonProperty("given_name")
  String givenName;

  @JsonProperty("family_name")
  String familyName;

  String picture;

  String locale;

  @Override
  public Users convertSocialInfoToUsers(){
    return Users.builder()
        .name(this.getName())
        .email(this.getEmail())
        .isSocial(YNEnum.Y)
        .socialId(this.getId())
        .socialType(SocialType.kakao)
        .picture(this.getPicture())
        .isUse(IsUseEnum.U)
        .build();
  }

}
