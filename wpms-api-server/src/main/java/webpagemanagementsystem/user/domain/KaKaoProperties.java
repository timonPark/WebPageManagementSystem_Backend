package webpagemanagementsystem.user.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class KaKaoProperties {
  String nickname;

  @JsonProperty("profile_image")
  String profileImage;

  @JsonProperty("thumbnail_image")
  String thumbnailImage;
}
