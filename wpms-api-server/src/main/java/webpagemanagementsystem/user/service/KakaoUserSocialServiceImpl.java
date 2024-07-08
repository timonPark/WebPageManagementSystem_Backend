package webpagemanagementsystem.user.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.NoSuchElementException;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.stereotype.Service;
import webpagemanagementsystem.common.jwt.AccessTokenProvider;
import webpagemanagementsystem.common.variable.SocialProperties;
import webpagemanagementsystem.user.domain.KakaoSocialInfo;
import webpagemanagementsystem.user.entity.Users;
import webpagemanagementsystem.user.exception.DeleteUserException;
import webpagemanagementsystem.user.exception.DuplicationRegisterException;
import webpagemanagementsystem.user.exception.NoUseException;
import webpagemanagementsystem.user.exception.SocialUnauthorizedException;

@Service("kakaoUserSocialService")
public class KakaoUserSocialServiceImpl extends UserSocialServiceImpl implements UserSocialService {

    public KakaoUserSocialServiceImpl(
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
        final KakaoSocialInfo kakaoSocialInfo = convertHashMapToGeneric(
            getSocialInfo(
                socialType,
                accessToken,
                socialProperties.platform.get(socialType).getBaseUrl(),
                socialProperties.platform.get(socialType).getPathUrl()
            ),
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
}
