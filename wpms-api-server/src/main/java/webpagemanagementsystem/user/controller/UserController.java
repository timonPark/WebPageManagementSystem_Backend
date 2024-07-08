package webpagemanagementsystem.user.controller;

import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import webpagemanagementsystem.user.service.UserSocialService;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final Map<String, UserSocialService> userSocialServiceMap;


    @PostMapping("/social/{socialType}")
    public ResponseEntity<String> kakaoLogin(@PathVariable("socialType") String socialType, @RequestBody SocialRequestDto socialRequestDto) {
        try {
            String accessToken = userSocialServiceMap.get(socialType + "UserSocialService").socialLoginProgress(socialRequestDto.getAccesstoken(), socialType);
            return ResponseEntity.ok(accessToken);
        } catch (SocialUnauthorizedException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.fillInStackTrace().getMessage());
        } catch (DuplicationRegisterException | NoUseException | DeleteUserException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.fillInStackTrace().getMessage());
        }

    }
}
