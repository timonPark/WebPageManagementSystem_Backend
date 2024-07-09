package webpagemanagementsystem.user.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class NaverResponse {
  String id;

  @JsonProperty("profile_image")
  String profileImage;

  String gender;

  String email;

  String mobile;

  @JsonProperty("mobile_e164")
  String mobileE164;

  String name;
}
