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
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import webpagemanagementsystem.common.entity.IsUseEnum;
import webpagemanagementsystem.common.variable.SocialProperties;
import webpagemanagementsystem.user.dto.FindByEmailResponse;
import webpagemanagementsystem.user.domain.KakaoSocialInfo;
import webpagemanagementsystem.user.entity.Users;
import webpagemanagementsystem.user.exception.SocialUnauthorizedException;
import webpagemanagementsystem.user.repository.UsersRepository;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UsersRepository usersRepository;

    private final SocialProperties socialProperties;

    private final ObjectMapper objectMapper;

    @Override
    public Users findByEmail(String email) {
        try {
            return usersRepository.findByEmailAndIsUse(email, IsUseEnum.U).stream().findFirst()
                .orElseThrow(() -> new NoSuchElementException());
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
        return null;
    }

    @Override
    public Users joinKakaoSocial(String accessToken) throws SocialUnauthorizedException {
        var socialInfo = getSocialInfo("kakao", accessToken, socialProperties.platform.get("kakao").getBaseUrl(), socialProperties.platform.get("kakao").getPathUrl());
        final KakaoSocialInfo kakaoSocialInfo = convertHashMapToGeneric(socialInfo, KakaoSocialInfo.class);
        return null;
    }

    @Override
    public Users joinNaverSocial(String accessToken) {
        return null;
    }

    @Override
    public Users joinGoogleSocial(String accessToken) {
        return null;
    }

    @Override
    public <T> T convertHashMapToGeneric(Map<String, Object> socialInfo, Class<T> classType) {
        final ObjectMapper mapper = objectMapper;
        return mapper.convertValue(socialInfo, classType);
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
}
