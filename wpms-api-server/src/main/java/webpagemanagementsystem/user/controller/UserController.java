package webpagemanagementsystem.user.controller;

import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import webpagemanagementsystem.user.dto.UserLoginRequestDto;
import webpagemanagementsystem.user.dto.UserLoginResponseDto;
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
    public ResponseEntity<UserLoginResponseDto> kakaoLogin(@RequestBody UserLoginRequestDto userLoginRequestDto) {
//        System.out.println("accessToken: " + userLoginRequestDto.getAccessToken());
//        String reqURL = "https://kapi.kakao.com/v2/user/me";
//        URI uri = UriComponentsBuilder
//                .fromUriString("https://kapi.kakao.com")
//                .path("/v2/user/me")
//                .encode().build().toUri();
//        System.out.println(uri.toString());
//
//        RestTemplate restTemplate = new RestTemplate();
//        HttpHeaders headers = new HttpHeaders();
//        headers.add("Authorization", "Bearer " + userLoginRequestDto.getAccessToken());
//        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
//
//        HttpEntity<MultiValueMap<String, String>> kakaoUserInfoRequest = new HttpEntity<>(headers);
//        ResponseEntity<String> responseEntity = restTemplate.exchange(
//                uri.toString(),
//                HttpMethod.POST,
//                kakaoUserInfoRequest,
//                String.class
//        );
//
//        System.out.println("카카오 사용자 정보 responseEntity = " + responseEntity);
//        System.out.println("카카오 사용자 정보 getStatusCode = " + responseEntity.getStatusCode());
        return ResponseEntity.ok(new UserLoginResponseDto("y"));
    }
}
