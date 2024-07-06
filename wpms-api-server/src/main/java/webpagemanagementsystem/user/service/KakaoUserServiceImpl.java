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
import webpagemanagementsystem.common.entity.IsUseEnum;
import webpagemanagementsystem.common.entity.YNEnum;
import webpagemanagementsystem.common.jwt.AccessTokenProvider;
import webpagemanagementsystem.common.variable.SocialProperties;
import webpagemanagementsystem.user.dto.FindByEmailResponse;
import webpagemanagementsystem.user.domain.KakaoSocialInfo;
import webpagemanagementsystem.user.entity.SocialType;
import webpagemanagementsystem.user.entity.Users;
import webpagemanagementsystem.user.exception.DeleteUserException;
import webpagemanagementsystem.user.exception.DuplicationRegisterException;
import webpagemanagementsystem.user.exception.NoUseException;
import webpagemanagementsystem.user.exception.SocialUnauthorizedException;
import webpagemanagementsystem.user.repository.UsersRepository;

@Service("kakaoUserService")
@RequiredArgsConstructor
public class KakaoUserServiceImpl implements UserService {

    private final AccessTokenProvider accessTokenProvider;

    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    private final UsersRepository usersRepository;

    private final SocialProperties socialProperties;

    private final ObjectMapper objectMapper;

    @Override
    public Users findByEmail(String email) {
        try {
            return usersRepository.findByEmailAndIsUse(email, IsUseEnum.U).stream().findFirst()
                .orElseThrow(NoSuchElementException::new);
        } catch(NoSuchElementException e) {
            return null;
        }

    }

    @Override
    public FindByEmailResponse convertUsersToFindByEmailResponse(Users user) {
        return user != null ? new FindByEmailResponse(user) : null;
    }

    @Override
    public Users createUser(Users user) {
        return usersRepository.save(user);
    }

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
            Users registUser = usersRepository.findByEmail(kakaoSocialInfo.convertKakaoSocialInfoToUsers().getEmail())
                .stream().findFirst()
                .orElseThrow(NoSuchElementException::new);

            // 중복 회원가입 방지
            preventDuplicationRegist(loginUser,registUser);

            // 로그인 시 휴면처리
            validateUserIsUse(registUser);

            // accessToken 발급
            return socialAuthenticate(registUser);
        } catch (NoSuchElementException e) {
            // 회원가입
            Users resultUser = createUser(kakaoSocialInfo.convertKakaoSocialInfoToUsers());

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

    @Override
    public void preventDuplicationRegist(Users loginUser, Users registerUser)
        throws DuplicationRegisterException {
        if (loginUser.getEmail().equals(registerUser.getEmail())){

            if (!loginUser.getIsSocial().name().equals(registerUser.getIsSocial().name())) {
                throw new DuplicationRegisterException(registerUser);
            } else {
                if (loginUser.getIsSocial() == YNEnum.Y) {
                    if (!loginUser.getSocialType().name().equals(registerUser.getSocialType().name())) {
                        throw new DuplicationRegisterException(registerUser);
                    }
                }
            }
        }
    }

    @Override
    public void validateUserIsUse(Users user) throws NoUseException, DeleteUserException {
        if (user.getIsUse().name().equals(IsUseEnum.N.name())) {
            throw new NoUseException();
        }
        if (user.getIsUse().name().equals(IsUseEnum.D.name())) {
            throw new DeleteUserException();
        }
    }


}
