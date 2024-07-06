package webpagemanagementsystem.user.service;

import java.util.Map;
import webpagemanagementsystem.user.dto.FindByEmailResponse;
import webpagemanagementsystem.user.entity.Users;
import webpagemanagementsystem.user.exception.DeleteUserException;
import webpagemanagementsystem.user.exception.DuplicationRegisterException;
import webpagemanagementsystem.user.exception.NoUseException;
import webpagemanagementsystem.user.exception.SocialUnauthorizedException;

public interface UserService {
    public Users findByEmail(String email);
    public FindByEmailResponse convertUsersToFindByEmailResponse(Users user);

    public Users createUser(Users user);

    public Map<String, Object> getSocialInfo(String provider, String accessToken, String baseSocaiUrl, String baseSocaiPathUrl)
        throws SocialUnauthorizedException;

    public String socialLoginProgress(String accessToken)
        throws SocialUnauthorizedException, DuplicationRegisterException, DeleteUserException, NoUseException;

    public <T> T convertHashMapToGeneric(Map<String, Object> socialInfo, Class<T> classType);

    public String socialAuthenticate(Users user);

    public void preventDuplicationRegist(Users loginUser, Users registerUser)
        throws DuplicationRegisterException;

    public void validateUserIsUse(Users user) throws NoUseException, DeleteUserException;
}
