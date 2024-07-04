package webpagemanagementsystem.user.exception;

public class NoUseException extends Exception{
  public NoUseException(){
    super("해당 계정은 휴면 계정입니다");
  }


}
