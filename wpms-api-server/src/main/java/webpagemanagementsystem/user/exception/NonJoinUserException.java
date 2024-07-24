package webpagemanagementsystem.user.exception;

public class NonJoinUserException extends Exception {
  public NonJoinUserException() { super("미가입 된 회원 입니다."); }
}
