package webpagemanagementsystem.user.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class KakaoProfile {
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
