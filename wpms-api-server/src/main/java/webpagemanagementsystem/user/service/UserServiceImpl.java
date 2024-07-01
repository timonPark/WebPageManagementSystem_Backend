package webpagemanagementsystem.user.service;

import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import webpagemanagementsystem.common.entity.IsUseEnum;
import webpagemanagementsystem.user.dto.FindByEmailResponse;
import webpagemanagementsystem.user.entity.Users;
import webpagemanagementsystem.user.repository.UsersRepository;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UsersRepository usersRepository;

    @Override
    public Users findByEmail(String email) {
        try {
            return usersRepository.findByEmailAndIsUse(email, IsUseEnum.U).stream().findFirst()
                .orElseThrow(() -> new NoSuchElementException());
        } catch(NoSuchElementException e) {
            return null;
        }

    }

    @Override
    public FindByEmailResponse convertUsersToFindByEmailResponse(Users user) {
        return user != null ? new FindByEmailResponse(user) : null;
    }
}
