package webpagemanagementsystem.user.exception;

public class SocialUnauthorizedException extends Exception{
  public SocialUnauthorizedException(String provider, String accessToken){
    super("소셜 " +  provider+ " 인증에 실패하였습니다. accessToken: " + accessToken);
  }
}
