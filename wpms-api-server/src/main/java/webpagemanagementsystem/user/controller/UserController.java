package webpagemanagementsystem.user.controller;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import webpagemanagementsystem.user.dto.SocialRequestDto;
import webpagemanagementsystem.user.dto.UserLoginRequestDto;
import webpagemanagementsystem.user.dto.UserLoginResponseDto;
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

    @PostMapping("/kakao")
    public ResponseEntity<UserLoginResponseDto> kakaoLogin(@RequestBody SocialRequestDto socialRequestDto)
        throws SocialUnauthorizedException {

        userService.joinKakaoSocial(socialRequestDto.getAccesstoken());
        return ResponseEntity.ok(new UserLoginResponseDto("y"));
    }
}
