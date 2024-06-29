package webpagemanagementsystem.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SocialUserDto {
    private String name;
    private String email;
    private String nickname;
    private String loginType;
    private String socialId;
    private String profilePicture;
}
