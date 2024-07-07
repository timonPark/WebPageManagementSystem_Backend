package webpagemanagementsystem.user.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
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
import webpagemanagementsystem.common.variable.SocialProperties;
import webpagemanagementsystem.user.domain.KakaoSocialInfo;
import webpagemanagementsystem.user.entity.SocialType;
import webpagemanagementsystem.user.entity.Users;
import webpagemanagementsystem.user.exception.DeleteUserException;
import webpagemanagementsystem.user.exception.DuplicationRegisterException;
import webpagemanagementsystem.user.exception.NoUseException;
import webpagemanagementsystem.user.exception.SocialUnauthorizedException;

@Service("kakaoUserSocialService")
@RequiredArgsConstructor
public class KakaoUserSocialServiceImpl implements UserSocialService {

    private final AccessTokenProvider accessTokenProvider;

    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    private final SocialProperties socialProperties;

    private final ObjectMapper objectMapper;

    private final UserService userService;

    @Override
    public String socialLoginProgress(String accessToken)
        throws SocialUnauthorizedException, DuplicationRegisterException, DeleteUserException, NoUseException {
        String socialType = SocialType.kakao.name();
        Map<String, Object> socialInfo = getSocialInfo(
            socialType,
            accessToken,
            socialProperties.platform.get(socialType).getBaseUrl(),
            socialProperties.platform.get(socialType).getPathUrl()
        );
        final KakaoSocialInfo kakaoSocialInfo = convertHashMapToGeneric(
            socialInfo,
            KakaoSocialInfo.class
        );
        Users loginUser = kakaoSocialInfo.convertKakaoSocialInfoToUsers();

        try {
            Users registUser = userService.findByEmail(kakaoSocialInfo.convertKakaoSocialInfoToUsers().getEmail())
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
            Users resultUser = userService.createUser(kakaoSocialInfo.convertKakaoSocialInfoToUsers());

            // accessToken 발급
            return socialAuthenticate(resultUser);
        }
    }

    @Override
    public <T> T convertHashMapToGeneric(Map<String, Object> socialInfo, Class<T> classType) {
        return objectMapper.convertValue(socialInfo, classType);
    }

    @Override
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
            return responseEntity.getBody();
        } catch (HttpClientErrorException e) {
            throw new SocialUnauthorizedException(provider, accessToken);
        }

    }

    @Override
    public String socialAuthenticate(Users user) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getEmail(), user.getSocialId());
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        return accessTokenProvider.createToken(authentication);
    }


}
