package webpagemanagementsystem.user.exception;

public class AuthenticationFailException extends Exception{
  public AuthenticationFailException() {
    super("로그인 인증에 실패하였습니다. 인증정보를 다시 확인해주세요");
  }

}
