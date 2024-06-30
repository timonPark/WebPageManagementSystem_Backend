package webpagemanagementsystem.user.service;

import webpagemanagementsystem.user.dto.FindByEmailResponse;
import webpagemanagementsystem.user.entity.Users;

public interface UserService {
    public Users findByEmail(String email);
    public FindByEmailResponse convertUsersToFindByEmailResponse(Users user);
}
