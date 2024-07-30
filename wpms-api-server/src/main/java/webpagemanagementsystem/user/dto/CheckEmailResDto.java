package webpagemanagementsystem.user.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CheckEmailResDto {
    private Boolean result;
    private String message;
}
