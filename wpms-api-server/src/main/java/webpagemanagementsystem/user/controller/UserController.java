package webpagemanagementsystem.user.controller;

import java.net.URI;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import webpagemanagementsystem.user.dto.UserLoginRequestDto;
import webpagemanagementsystem.user.dto.UserLoginResponseDto;

@RestController
@RequestMapping("/user")
public class UserController {

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
