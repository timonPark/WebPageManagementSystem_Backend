package webpagemanagementsystem.user.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDateTime;
import lombok.Data;
import webpagemanagementsystem.common.entity.IsUseEnum;
import webpagemanagementsystem.common.entity.YNEnum;
import webpagemanagementsystem.user.entity.SocialType;
import webpagemanagementsystem.user.entity.Users;

@Data
public class KakaoSocialInfo {
  Long id;

  @JsonProperty("connected_at")
  LocalDateTime connectedAt;

  @JsonProperty("kakao_account")
  KakaoAccount kakaoAccount;

  KaKaoProperties properties;

  public Users convertKakaoSocialInfoToUsers(){
    return Users.builder()
        .name(this.getKakaoAccount().getProfile().getNickname())
        .email(this.getKakaoAccount().getEmail())
        .isSocial(YNEnum.Y)
        .socialId(this.getId().toString())
        .socialType(SocialType.kakao)
        .picture(this.getKakaoAccount().getProfile().getProfileImageUrl())
        .isUse(IsUseEnum.U)
        .build();
  }
}


