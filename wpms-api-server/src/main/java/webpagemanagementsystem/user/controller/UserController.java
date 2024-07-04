package webpagemanagementsystem.user.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import webpagemanagementsystem.user.dto.SocialRequestDto;
import webpagemanagementsystem.user.exception.DeleteUserException;
import webpagemanagementsystem.user.exception.DuplicationRegisterException;
import webpagemanagementsystem.user.exception.NoUseException;
import webpagemanagementsystem.user.exception.SocialUnauthorizedException;
import webpagemanagementsystem.user.service.UserService;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/email/{email}")
    public ResponseEntity findByEmail(@PathVariable("email") String email) {
        return ResponseEntity.ok(userService.convertUsersToFindByEmailResponse(userService.findByEmail(email)));
    }

    @PostMapping("/social/kakao")
    public ResponseEntity<String> kakaoLogin(@RequestBody SocialRequestDto socialRequestDto) {
        try {
            String accessToken = userService.kakaoSocialLoginProgress(socialRequestDto.getAccesstoken());
            return ResponseEntity.ok(accessToken);
        } catch (SocialUnauthorizedException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.fillInStackTrace().getMessage());
        } catch (DuplicationRegisterException | NoUseException | DeleteUserException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.fillInStackTrace().getMessage());
        }

    }
}
