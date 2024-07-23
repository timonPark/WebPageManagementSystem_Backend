package webpagemanagementsystem.user.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SignUpResDto {
  private Long userNo;
  private String name;
  private String email;
}
