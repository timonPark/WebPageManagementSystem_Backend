package webpagemanagementsystem.user.service;

import java.util.List;
import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import webpagemanagementsystem.common.entity.IsUseEnum;
import webpagemanagementsystem.common.entity.YNEnum;
import webpagemanagementsystem.common.jwt.AccessTokenProvider;
import webpagemanagementsystem.user.dto.*;
import webpagemanagementsystem.user.entity.Users;
import webpagemanagementsystem.user.exception.*;
import webpagemanagementsystem.user.repository.UsersRepository;
@Service
@RequiredArgsConstructor
public class UserServiceImple implements UserService{

  private final UsersRepository usersRepository;

  private final PasswordEncoder passwordEncoder;

  private final AuthenticationManagerBuilder authenticationManagerBuilder;

  private final AccessTokenProvider accessTokenProvider;

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
  public boolean checkEmail(String email) throws DuplicateEmailException {
    var result = findByEmailAndIsUseY(email);
    if (result == null) {
      return true;
    }
    throw new DuplicateEmailException();
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

  @Override
  public SignUpResDto signUp(SignUpReqDto signUpReqDto) throws DuplicateEmailException {
    try {
      checkEmail(signUpReqDto.getEmail());
    } catch (Exception e) {
      throw new DuplicateEmailException();
    }

    Users user = this.usersRepository.save(
            Users.builder()
                    .name(signUpReqDto.getName())
                    .email(signUpReqDto.getEmail())
                    .password(passwordEncoder.encode(signUpReqDto.getPassword()))
                    .isSocial(YNEnum.N)
                    .isUse(IsUseEnum.U)
                    .build()
    );
    return convertUsersToSignUpResDto(user);
  }

  @Override
  public SignUpResDto convertUsersToSignUpResDto(Users users) {
    return SignUpResDto.builder()
        .userNo(users.getUserNo())
        .name(users.getName())
        .email(users.getEmail())
        .build();
  }

  @Override
  public String login(LoginReqDto loginReqDto)
      throws NonJoinUserException, AuthenticationFailException {
    if (findByEmailAndIsUseY(loginReqDto.getEmail()) == null) {
      throw new NonJoinUserException();
    }
    try {
      UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginReqDto.getEmail(), loginReqDto.getPassword());
      Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
      return accessTokenProvider.createToken(authentication);
    } catch (Exception e) {
      throw new AuthenticationFailException();
    }
  }

  @Override
  public GetUserResDto getUser(String email) {
    return new GetUserResDto(findByEmailAndIsUseY(email));
  }
}
