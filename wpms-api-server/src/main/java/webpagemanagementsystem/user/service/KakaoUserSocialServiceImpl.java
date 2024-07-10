package webpagemanagementsystem.user.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.stereotype.Service;
import webpagemanagementsystem.common.jwt.AccessTokenProvider;
import webpagemanagementsystem.common.variable.SocialProperties;
import webpagemanagementsystem.user.domain.KakaoSocialInfo;
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
        super(accessTokenProvider, authenticationManagerBuilder, objectMapper, userService);
        this.socialProperties = socialProperties;
    }

    private final SocialProperties socialProperties;

    @Override
    public String socialLoginProgress(String accessToken, String socialType)
        throws SocialUnauthorizedException, DuplicationRegisterException, DeleteUserException, NoUseException {
        return returnSocialLoginProgress(convertHashMapToGeneric(
            getSocialInfo(
                socialType,
                accessToken,
                socialProperties.platform.get(socialType).getBaseUrl(),
                socialProperties.platform.get(socialType).getPathUrl()
            ),
            KakaoSocialInfo.class
        ).convertKakaoSocialInfoToUsers());
    }
}
