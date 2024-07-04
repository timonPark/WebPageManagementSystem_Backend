package webpagemanagementsystem.security;

import java.util.List;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import webpagemanagementsystem.role.Role;
import webpagemanagementsystem.user.entity.Users;

public class UserAdapter extends User {

  public UserAdapter(Users user) {
    super(
        user.getEmail(),
        user.getPassword(),
        authorities(List.of(Role.USER))
    );
  }

  private static List<SimpleGrantedAuthority> authorities(List<Role> roles) {
    return roles.stream()
        .map(role -> new SimpleGrantedAuthority(role.name())).toList();
  }

}
