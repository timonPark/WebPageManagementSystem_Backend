package webpagemanagementsystem.user.service;

import java.net.URI;
import java.util.Map;
import java.util.NoSuchElementException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import webpagemanagementsystem.common.jwt.AccessTokenProvider;
import webpagemanagementsystem.common.variable.SocialProperties;
import webpagemanagementsystem.user.domain.GoogleSocialInfo;
import webpagemanagementsystem.user.domain.KakaoSocialInfo;
import webpagemanagementsystem.user.domain.NaverSocialInfo;
import webpagemanagementsystem.user.domain.SocialInfo;
import webpagemanagementsystem.user.entity.Users;
import webpagemanagementsystem.user.exception.DeleteUserException;
import webpagemanagementsystem.user.exception.DuplicationRegisterException;
import webpagemanagementsystem.user.exception.NoUseException;
import webpagemanagementsystem.user.exception.SocialUnauthorizedException;

@Service
public class UserSocialServiceImpl implements  UserSocialService{

  public UserSocialServiceImpl(
      AccessTokenProvider accessTokenProvider,
      AuthenticationManagerBuilder authenticationManagerBuilder,
      UserService userService,
      SocialProperties socialProperties
      ) {
    this.accessTokenProvider = accessTokenProvider;
    this.authenticationManagerBuilder = authenticationManagerBuilder;
    this.userService = userService;
    this.socialProperties = socialProperties;
    this.socialInfoMap = Map.of(
        "kakao", KakaoSocialInfo.class,
        "naver", NaverSocialInfo.class,
        "google", GoogleSocialInfo.class
    );
  }

  private final AccessTokenProvider accessTokenProvider;

  private final AuthenticationManagerBuilder authenticationManagerBuilder;

  private final UserService userService;

  private final SocialProperties socialProperties;

  private final Map<String, Class<? extends SocialInfo>> socialInfoMap;

  public <T extends SocialInfo> String socialLoginProgress(String accessToken, String socialType)
      throws SocialUnauthorizedException, DuplicationRegisterException, DeleteUserException, NoUseException {
    return returnSocialLoginProgress(
        getSocialInfo(
            socialType,
            accessToken,
            socialProperties.platform.get(socialType).getBaseUrl(),
            socialProperties.platform.get(socialType).getPathUrl(),
            socialInfoMap.get(socialType)
        ).convertSocialInfoToUsers()
    );
  }

    public <T extends SocialInfo> T getSocialInfo(String provider, String accessToken, String baseSocaiUrl, String baseSocaiPathUrl, Class<T> socialInfoType)
      throws SocialUnauthorizedException{
    URI uri = UriComponentsBuilder
        .fromUriString(baseSocaiUrl)
        .path(baseSocaiPathUrl)
        .encode().build().toUri();

    final HttpHeaders headers = new HttpHeaders();
    headers.add("Authorization", "Bearer " + accessToken);
    headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
    try {
      return (new RestTemplate().exchange(uri, HttpMethod.GET, new HttpEntity<>(headers), socialInfoType)).getBody();
    } catch (HttpClientErrorException e) {
      throw new SocialUnauthorizedException(provider, accessToken);
    }

  }


  public String socialAuthenticate(Users user) {
    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getEmail(), user.getSocialId());
    Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
    return accessTokenProvider.createToken(authentication);
  }

  public String returnSocialLoginProgress(Users loginUser)
      throws DuplicationRegisterException, DeleteUserException, NoUseException {
    try {
      Users registUser = userService.findByEmail(loginUser.getEmail())
          .stream().findFirst()
          .orElseThrow(NoSuchElementException::new);

      // 중복 회원가입 방지
      userService.preventDuplicationRegist(loginUser,registUser);

      // 로그인 시 휴면처리
      userService.validateUserIsUse(registUser);

      // accessToken 발급
      return socialAuthenticate(registUser);
    } catch (NoSuchElementException e) {
      // 회원가입
      Users resultUser = userService.createUser(loginUser);

      // accessToken 발급
      return socialAuthenticate(resultUser);
    }
  }
}
