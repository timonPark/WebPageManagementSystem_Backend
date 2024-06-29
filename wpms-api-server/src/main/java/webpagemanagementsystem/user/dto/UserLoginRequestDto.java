package webpagemanagementsystem.user.dto;

import lombok.Getter;

public class UserLoginRequestDto {
    @Getter
    private String initLoginYn;

    public UserLoginRequestDto(String initLoginYn) {
        this.initLoginYn = initLoginYn;
    }



}
