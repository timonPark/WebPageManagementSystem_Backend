package webpagemanagementsystem.user.exception;

public class DuplicateEmailException extends Exception {
    public DuplicateEmailException() {
        super("이미 가입된 이메일 입니다");
    }
}
