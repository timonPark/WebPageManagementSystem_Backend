package webpagemanagementsystem.common.graphql;

import lombok.AllArgsConstructor;
import lombok.Builder;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Builder
@AllArgsConstructor
public class ApiResponseFormat <T> {
  private final String statusCode;
  private final T data;
  private final String message;

  public static <T> ApiResponseFormat<T> success(HttpStatus httpStatus, T data) {
    return ApiResponseFormat.<T>builder()
        .statusCode(String.valueOf(httpStatus.value()))
        .data(data)
        .message("")
        .build();
  }

  public static <T> ApiResponseFormat<T> error(HttpStatus httpStatus, String errorMessage) {
    return ApiResponseFormat.<T>builder()
        .statusCode(String.valueOf(httpStatus.value()))
        .data(null)
        .message(errorMessage)
        .build();
  }
}
