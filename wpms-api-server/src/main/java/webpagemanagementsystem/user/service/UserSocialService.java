package webpagemanagementsystem.user.service;

import java.util.Map;
import webpagemanagementsystem.user.domain.SocialInfo;
import webpagemanagementsystem.user.entity.Users;
import webpagemanagementsystem.user.exception.DeleteUserException;
import webpagemanagementsystem.user.exception.DuplicationRegisterException;
import webpagemanagementsystem.user.exception.NoUseException;
import webpagemanagementsystem.user.exception.SocialUnauthorizedException;

public interface UserSocialService {

  public <T extends SocialInfo> String socialLoginProgress(String accessToken, String socialType)
      throws SocialUnauthorizedException, DuplicationRegisterException, DeleteUserException, NoUseException;

  public <T extends SocialInfo> T getSocialInfo(String provider, String accessToken, String baseSocaiUrl, String baseSocaiPathUrl, Class<T> socialInfoType)
      throws SocialUnauthorizedException;

  public String socialAuthenticate(Users user);


}
