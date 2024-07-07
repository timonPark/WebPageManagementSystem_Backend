package webpagemanagementsystem.user.service;

import java.util.List;
import webpagemanagementsystem.user.dto.FindByEmailResponse;
import webpagemanagementsystem.user.entity.Users;
import webpagemanagementsystem.user.exception.DeleteUserException;
import webpagemanagementsystem.user.exception.DuplicationRegisterException;
import webpagemanagementsystem.user.exception.NoUseException;

public interface UserService {
    public Users findByEmailAndIsUseY(String email);

    public List<Users> findByEmail(String email);
    public FindByEmailResponse convertUsersToFindByEmailResponse(Users user);

    public Users createUser(Users user);

    public void preventDuplicationRegist(Users loginUser, Users registerUser)
        throws DuplicationRegisterException;

    public void validateUserIsUse(Users user) throws NoUseException, DeleteUserException;
}
