package webpagemanagementsystem.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import webpagemanagementsystem.common.entity.YNEnum;
import webpagemanagementsystem.user.entity.Users;
import webpagemanagementsystem.user.service.UserService;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

  private final UserService userService;

  private final PasswordEncoder passwordEncoder;

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    return new UserAdapter(isSocialTypeYInputPasswordToEncodingSocialId(userService.findByEmailAndIsUseY(email)));
  }

  public Users isSocialTypeYInputPasswordToEncodingSocialId (Users user) {
    if (user.getIsSocial() == YNEnum.Y) {
      user.setPassword(passwordEncoder.encode(user.getSocialId()));
    }
    return user;
  }


}
