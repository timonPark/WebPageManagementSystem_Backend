package webpagemanagementsystem.user.exception;

import webpagemanagementsystem.common.entity.YNEnum;
import webpagemanagementsystem.user.entity.Users;

public class DuplicationRegisterException extends Exception{
  public DuplicationRegisterException(Users user) {
    super(
        "이미 가입되어 있는 계정입니다. 소셜로그인여부: " + user.getIsSocial().name()
            + " 소셜타입: " + (user.getIsSocial().name().equals(YNEnum.Y.name())
            ? user.getSocialType().name()
            : "일반로그인")
    );
  }

}
