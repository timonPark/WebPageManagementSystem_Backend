package webpagemanagementsystem.user.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.stereotype.Service;
import webpagemanagementsystem.common.jwt.AccessTokenProvider;
import webpagemanagementsystem.common.variable.SocialProperties;
import webpagemanagementsystem.user.domain.NaverSocialInfo;
import webpagemanagementsystem.user.entity.Users;
import webpagemanagementsystem.user.exception.DeleteUserException;
import webpagemanagementsystem.user.exception.DuplicationRegisterException;
import webpagemanagementsystem.user.exception.NoUseException;
import webpagemanagementsystem.user.exception.SocialUnauthorizedException;

@Service("naverUserSocialService")

public class NaverUserSocialServiceImpl extends UserSocialServiceImpl implements UserSocialService{
  public NaverUserSocialServiceImpl(
      AccessTokenProvider accessTokenProvider,
      AuthenticationManagerBuilder authenticationManagerBuilder,
      SocialProperties socialProperties,
      ObjectMapper objectMapper,
      UserService userService
      ) {
    super(accessTokenProvider, authenticationManagerBuilder, objectMapper);
    this.socialProperties = socialProperties;
    this.userService = userService;
  }

  private final SocialProperties socialProperties;

  private final UserService userService;

  @Override
  public String socialLoginProgress(String accessToken, String socialType)
      throws SocialUnauthorizedException, DuplicationRegisterException, DeleteUserException, NoUseException {
    final NaverSocialInfo naverSocialInfo = convertHashMapToGeneric(
        getSocialInfo(
            socialType,
            accessToken,
            socialProperties.platform.get(socialType).getBaseUrl(),
            socialProperties.platform.get(socialType).getPathUrl()
        ),
        NaverSocialInfo.class
    );
    return null;
  }

  @Override
  public <T> T convertHashMapToGeneric(Map<String, Object> socialInfo, Class<T> classType) {
    return null;
  }

  @Override
  public String socialAuthenticate(Users user) {
    return null;
  }
}
