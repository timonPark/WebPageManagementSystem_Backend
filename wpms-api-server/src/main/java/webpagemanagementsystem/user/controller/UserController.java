package webpagemanagementsystem.user.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import webpagemanagementsystem.common.response.ApiResponse;
import webpagemanagementsystem.user.dto.*;
import webpagemanagementsystem.user.exception.AuthenticationFailException;
import webpagemanagementsystem.user.exception.DeleteUserException;
import webpagemanagementsystem.user.exception.DuplicationRegisterException;
import webpagemanagementsystem.user.exception.NoUseException;
import webpagemanagementsystem.user.exception.NonJoinUserException;
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
    public ApiResponse<AuthenticationSuccessfulDto> socialLogin(@PathVariable("socialType") String socialType, @RequestBody SocialRequestDto socialRequestDto)
        throws SocialUnauthorizedException, DuplicationRegisterException, DeleteUserException, NoUseException {
        String accessToken = userSocialService.socialLoginProgress(socialRequestDto.getAccesstoken(), socialType);
        return  ApiResponse.createSuccess(new AuthenticationSuccessfulDto(accessToken));
    }

    @GetMapping("/checkEmail/{email}")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<Boolean> checkEmail(@PathVariable("email") String email) throws NoUseException {
        return ApiResponse.createSuccess(userService.checkEmail(email));
    }

    @PostMapping("/signUp")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<SignUpResDto> signUp(final @Valid @RequestBody SignUpReqDto signUpReqDto) {
        return ApiResponse.createSuccess( userService.signUp(signUpReqDto));
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<AuthenticationSuccessfulDto> login(final @Valid @RequestBody LoginReqDto loginReqDto)
        throws AuthenticationFailException, NonJoinUserException {
        return ApiResponse.createSuccess(new AuthenticationSuccessfulDto(userService.login(loginReqDto)));
    }
}
