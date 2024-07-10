package webpagemanagementsystem.user.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import webpagemanagementsystem.common.jwt.AccessTokenProvider;
import webpagemanagementsystem.common.variable.SocialProperties;
import webpagemanagementsystem.user.domain.GoogleSocialInfo;
import webpagemanagementsystem.user.domain.KakaoSocialInfo;
import webpagemanagementsystem.user.exception.DeleteUserException;
import webpagemanagementsystem.user.exception.DuplicationRegisterException;
import webpagemanagementsystem.user.exception.NoUseException;
import webpagemanagementsystem.user.exception.SocialUnauthorizedException;

@Service("googleUserSocialService")
public class GoogleUserSocialServiceImpl extends UserSocialServiceImpl implements UserSocialService  {

  public GoogleUserSocialServiceImpl(
      AccessTokenProvider accessTokenProvider,
      AuthenticationManagerBuilder authenticationManagerBuilder,
      SocialProperties socialProperties,
      ObjectMapper objectMapper,
      UserService userService
  ) {
    super(accessTokenProvider, authenticationManagerBuilder, objectMapper, userService);
    this.socialProperties = socialProperties;
  }

  private final SocialProperties socialProperties;

  @Override
  public String socialLoginProgress(String accessToken, String socialType)
      throws SocialUnauthorizedException, DuplicationRegisterException, DeleteUserException, NoUseException {
    return returnSocialLoginProgress(
        getGoogleSocialInfo(
            socialType,
            accessToken,
            socialProperties.platform.get(socialType).getBaseUrl(),
            socialProperties.platform.get(socialType).getPathUrl()
        ).convertKakaoSocialInfoToUsers()
      );
  }


  public GoogleSocialInfo getGoogleSocialInfo(String provider, String accessToken, String baseSocaiUrl, String baseSocaiPathUrl)
      throws SocialUnauthorizedException {
    URI uri = UriComponentsBuilder
        .fromUriString(baseSocaiUrl)
        .path(baseSocaiPathUrl+accessToken)
        .encode().build().toUri();

    final HttpHeaders headers = new HttpHeaders();
    headers.add("Authorization", "Bearer " + accessToken);
    headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

    final HttpEntity httpEntity = new HttpEntity<>(headers);
    try {
      ResponseEntity<GoogleSocialInfo> responseEntity = new RestTemplate().exchange(uri, HttpMethod.GET, httpEntity, GoogleSocialInfo.class);
      System.out.println(responseEntity);
      GoogleSocialInfo result = responseEntity.getBody();
      return result;
    } catch (HttpClientErrorException e) {
      throw new SocialUnauthorizedException(provider, accessToken);
    }

  }
}
