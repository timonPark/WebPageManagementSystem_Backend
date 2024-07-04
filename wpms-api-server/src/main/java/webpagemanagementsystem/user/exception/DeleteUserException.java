package webpagemanagementsystem.user.exception;

public class DeleteUserException extends Exception{
  public DeleteUserException(){
    super("해당 계정은 삭제 되었습니다");
  }
}
