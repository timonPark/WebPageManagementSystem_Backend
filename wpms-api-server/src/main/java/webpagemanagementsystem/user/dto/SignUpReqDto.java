package webpagemanagementsystem.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class SignUpReqDto {

  @NotNull
  private String name;

  @Email
  private String email;

  @NotNull
  @Size(max=20, min=8)
  private String password;
}
