package webpagemanagementsystem.user.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import webpagemanagementsystem.common.entity.IsUseEnum;
import webpagemanagementsystem.user.entity.Users;

public interface UsersRepository extends JpaRepository<Users, Long> {
    public List<Users> findByEmailAndIsUse(String email, IsUseEnum isUse);
    public List<Users> findByEmail(String email);
}
