package webpagemanagementsystem.user.domain;

import lombok.Data;
import org.springframework.http.HttpStatusCode;
import webpagemanagementsystem.common.entity.IsUseEnum;
import webpagemanagementsystem.common.entity.YNEnum;
import webpagemanagementsystem.user.entity.SocialType;
import webpagemanagementsystem.user.entity.Users;

@Data
public class NaverSocialInfo implements SocialInfo{
  NaverResponse response;
  String resultcode;
  String message;

  public Users convertSocialInfoToUsers(){
    return Users.builder()
        .name(this.response.getName())
        .email(this.response.getEmail())
        .isSocial(YNEnum.Y)
        .socialId(this.response.getId())
        .socialType(SocialType.naver)
        .picture(this.response.getProfileImage())
        .isUse(IsUseEnum.U)
        .build();

  }


}
