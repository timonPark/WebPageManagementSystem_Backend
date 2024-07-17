package webpagemanagementsystem.user.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import webpagemanagementsystem.common.response.ApiResponse;
import webpagemanagementsystem.user.dto.SocialRequestDto;
import webpagemanagementsystem.user.dto.SocialResponseDto;
import webpagemanagementsystem.user.exception.DeleteUserException;
import webpagemanagementsystem.user.exception.DuplicationRegisterException;
import webpagemanagementsystem.user.exception.NoUseException;
import webpagemanagementsystem.user.exception.SocialUnauthorizedException;
import webpagemanagementsystem.user.service.UserSocialService;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserSocialService userSocialService;

    @PostMapping("/social/{socialType}")
    public ApiResponse<SocialResponseDto> kakaoLogin(@PathVariable("socialType") String socialType, @RequestBody SocialRequestDto socialRequestDto)
        throws SocialUnauthorizedException, DuplicationRegisterException, DeleteUserException, NoUseException {
        String accessToken = userSocialService.socialLoginProgress(socialRequestDto.getAccesstoken(), socialType);
        return  ApiResponse.createSuccess(new SocialResponseDto(accessToken));
    }
}
