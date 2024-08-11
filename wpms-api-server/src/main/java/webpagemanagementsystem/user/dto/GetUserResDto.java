package webpagemanagementsystem.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import webpagemanagementsystem.common.entity.YNEnum;
import webpagemanagementsystem.user.entity.SocialType;
import webpagemanagementsystem.user.entity.Users;

@Getter

public class GetUserResDto {
  private final Long userNo;
  private final String name;
  private final String email;
  private final YNEnum isSocial;
  private final String socialId;
  private final SocialType socialType;
  private final String picture;

  public GetUserResDto(Users user) {
    this.userNo = user.getUserNo();
    this.name = user.getName();
    this.email = user.getEmail();
    this.isSocial = user.getIsSocial();
    this.socialId = user.getSocialId();
    this.socialType = user.getSocialType();
    this.picture = user.getPicture();
  }
}
