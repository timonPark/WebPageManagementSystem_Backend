package webpagemanagementsystem.user.service;

import java.util.Map;
import webpagemanagementsystem.user.dto.FindByEmailResponse;
import webpagemanagementsystem.user.entity.Users;
import webpagemanagementsystem.user.exception.SocialUnauthorizedException;

public interface UserService {
    public Users findByEmail(String email);
    public FindByEmailResponse convertUsersToFindByEmailResponse(Users user);

    public Users createUser(Users user);

    public Map<String, Object> getSocialInfo(String provider, String accessToken, String baseSocaiUrl, String baseSocaiPathUrl)
        throws SocialUnauthorizedException;

    public Users joinKakaoSocial(String accessToken) throws SocialUnauthorizedException;

    public Users joinNaverSocial(String accessToken);

    public Users joinGoogleSocial(String accessToken);

    public <T> T convertHashMapToGeneric(Map<String, Object> socialInfo, Class<T> classType);
}
