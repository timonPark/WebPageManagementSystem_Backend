package webpagemanagementsystem.user.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import webpagemanagementsystem.common.jwt.AccessTokenProvider;
import webpagemanagementsystem.user.entity.Users;
import webpagemanagementsystem.user.exception.SocialUnauthorizedException;

@Service
@RequiredArgsConstructor
public class UserSocialServiceImpl {

  private final AccessTokenProvider accessTokenProvider;

  private final AuthenticationManagerBuilder authenticationManagerBuilder;

  private final ObjectMapper objectMapper;
  public Map<String, Object> getSocialInfo(String provider, String accessToken, String baseSocaiUrl, String baseSocaiPathUrl)
      throws SocialUnauthorizedException {
    URI uri = UriComponentsBuilder
        .fromUriString(baseSocaiUrl)
        .path(baseSocaiPathUrl)
        .encode().build().toUri();

    HttpHeaders headers = new HttpHeaders();
    headers.add("Authorization", "Bearer " + accessToken);
    headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

    HttpEntity<MultiValueMap<String, Object>> kakaoUserInfoRequest = new HttpEntity<>(headers);
    try {
      ResponseEntity<HashMap> responseEntity = new RestTemplate().postForEntity(
          uri.toString(),
          kakaoUserInfoRequest,
          HashMap.class
      );
      System.out.println(responseEntity);
      return responseEntity.getBody();
    } catch (HttpClientErrorException e) {
      throw new SocialUnauthorizedException(provider, accessToken);
    }

  }

  public <T> T convertHashMapToGeneric(Map<String, Object> socialInfo, Class<T> classType) {
    return objectMapper.convertValue(socialInfo, classType);
  }

  public String socialAuthenticate(Users user) {
    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getEmail(), user.getSocialId());
    Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
    return accessTokenProvider.createToken(authentication);
  }
}
