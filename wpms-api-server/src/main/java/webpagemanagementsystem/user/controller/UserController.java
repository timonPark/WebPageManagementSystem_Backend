package webpagemanagementsystem.user.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import webpagemanagementsystem.common.response.ApiResponse;
import webpagemanagementsystem.user.dto.SignUpReqDto;
import webpagemanagementsystem.user.dto.SignUpResDto;
import webpagemanagementsystem.user.dto.SocialRequestDto;
import webpagemanagementsystem.user.dto.SocialResponseDto;
import webpagemanagementsystem.user.exception.DeleteUserException;
import webpagemanagementsystem.user.exception.DuplicationRegisterException;
import webpagemanagementsystem.user.exception.NoUseException;
import webpagemanagementsystem.user.exception.SocialUnauthorizedException;
import webpagemanagementsystem.user.service.UserService;
import webpagemanagementsystem.user.service.UserSocialService;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    private final UserSocialService userSocialService;

    @PostMapping("/social/{socialType}")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<SocialResponseDto> socialLogin(@PathVariable("socialType") String socialType, @RequestBody SocialRequestDto socialRequestDto)
        throws SocialUnauthorizedException, DuplicationRegisterException, DeleteUserException, NoUseException {
        String accessToken = userSocialService.socialLoginProgress(socialRequestDto.getAccesstoken(), socialType);
        return  ApiResponse.createSuccess(new SocialResponseDto(accessToken));
    }

    @PostMapping("/signUp")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<SignUpResDto> signUp(final @Valid @RequestBody SignUpReqDto signUpReqDto) {
        return ApiResponse.createSuccess( userService.signUp(signUpReqDto));
    }
}
