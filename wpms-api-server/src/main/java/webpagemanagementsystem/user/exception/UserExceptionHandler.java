package webpagemanagementsystem.user.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import webpagemanagementsystem.common.response.ApiResponse;

@RestControllerAdvice(basePackages = "webpagemanagementsystem.user")
public class UserExceptionHandler {
  @ExceptionHandler(DeleteUserException.class)
  public ResponseEntity<ApiResponse<?>> handleDeleteUserException(Exception exception) {
    return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ApiResponse.createError(exception.getMessage()));
  }

  @ExceptionHandler(DuplicationRegisterException.class)
  public ResponseEntity<ApiResponse<?>> handleDuplicationRegisterException(Exception exception) {
    return ResponseEntity.status(HttpStatus.CONFLICT).body(ApiResponse.createError(exception.getMessage()));
  }

  @ExceptionHandler(NoUseException.class)
  public ResponseEntity<ApiResponse<?>> handleDNoUseException(Exception exception) {
    return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ApiResponse.createError(exception.getMessage()));
  }

  @ExceptionHandler(SocialUnauthorizedException.class)
  public ResponseEntity<ApiResponse<?>> handleSocialUnauthorizedException(Exception exception) {
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ApiResponse.createError(exception.getMessage()));
  }

  @ExceptionHandler(NonJoinUserException.class)
  public ResponseEntity<ApiResponse<?>> handleNonJoinUserException(Exception exception) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.createError(exception.getMessage()));
  }

  @ExceptionHandler(AuthenticationFailException.class)
  public ResponseEntity<ApiResponse<?>> handleAuthenticationFailException(Exception exception) {
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ApiResponse.createError(exception.getMessage()));
  }
}
