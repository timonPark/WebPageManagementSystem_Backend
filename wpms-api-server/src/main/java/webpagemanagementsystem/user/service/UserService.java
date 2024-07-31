package webpagemanagementsystem.user.service;

import java.util.List;

import webpagemanagementsystem.user.dto.*;
import webpagemanagementsystem.user.entity.Users;
import webpagemanagementsystem.user.exception.*;

public interface UserService {
    public Users findByEmailAndIsUseY(String email);

    public List<Users> findByEmail(String email);

    public boolean checkEmail(String email) throws DuplicateEmailException;
    public FindByEmailResponse convertUsersToFindByEmailResponse(Users user);

    public Users createUser(Users user);

    public void preventDuplicationRegist(Users loginUser, Users registerUser)
        throws DuplicationRegisterException;

    public void validateUserIsUse(Users user) throws NoUseException, DeleteUserException;

    public SignUpResDto signUp(SignUpReqDto signUpReqDto);

    public SignUpResDto convertUsersToSignUpResDto(Users users);

    public String login(LoginReqDto loginReqDto)
        throws NonJoinUserException, AuthenticationFailException;
}
