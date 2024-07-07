package webpagemanagementsystem.user.service;

import java.util.List;
import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import webpagemanagementsystem.common.entity.IsUseEnum;
import webpagemanagementsystem.common.entity.YNEnum;
import webpagemanagementsystem.user.dto.FindByEmailResponse;
import webpagemanagementsystem.user.entity.Users;
import webpagemanagementsystem.user.exception.DeleteUserException;
import webpagemanagementsystem.user.exception.DuplicationRegisterException;
import webpagemanagementsystem.user.exception.NoUseException;
import webpagemanagementsystem.user.repository.UsersRepository;
@Service
@RequiredArgsConstructor
public class UserServiceImple implements UserService{

  private final UsersRepository usersRepository;

  @Override
  public Users findByEmailAndIsUseY(String email) {
    try {
      return usersRepository.findByEmailAndIsUse(email, IsUseEnum.U).stream().findFirst()
          .orElseThrow(NoSuchElementException::new);
    } catch(NoSuchElementException e) {
      return null;
    }
  }

  @Override
  public List<Users> findByEmail(String email) {
    return usersRepository.findByEmail(email);
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
