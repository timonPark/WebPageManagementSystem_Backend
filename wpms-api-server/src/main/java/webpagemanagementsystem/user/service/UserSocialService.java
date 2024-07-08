package webpagemanagementsystem.user.service;

import java.util.Map;
import webpagemanagementsystem.user.entity.Users;
import webpagemanagementsystem.user.exception.DeleteUserException;
import webpagemanagementsystem.user.exception.DuplicationRegisterException;
import webpagemanagementsystem.user.exception.NoUseException;
import webpagemanagementsystem.user.exception.SocialUnauthorizedException;

public interface UserSocialService {

  public String socialLoginProgress(String accessToken, String socialType)
      throws SocialUnauthorizedException, DuplicationRegisterException, DeleteUserException, NoUseException;

  public <T> T convertHashMapToGeneric(Map<String, Object> socialInfo, Class<T> classType);

  public String socialAuthenticate(Users user);

}
