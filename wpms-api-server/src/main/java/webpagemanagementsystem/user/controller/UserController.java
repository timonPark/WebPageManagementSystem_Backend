package webpagemanagementsystem.user.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import webpagemanagementsystem.common.response.ApiResponse;
import webpagemanagementsystem.user.dto.*;
import webpagemanagementsystem.user.exception.*;
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
        String accessToken = userSocialService.socialLoginProgress(socialRequestDto.getAccessToken(), socialType);
        return  ApiResponse.createSuccess(new AuthenticationSuccessfulDto(accessToken));
    }

    @GetMapping("/checkEmail/{email}")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<Boolean> checkEmail(@PathVariable("email") String email) throws DuplicateEmailException {
        return ApiResponse.createSuccess(userService.checkEmail(email));
    }

    @PostMapping("/signUp")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<SignUpResDto> signUp(final @Valid @RequestBody SignUpReqDto signUpReqDto) throws DuplicateEmailException {
        var result = userService.signUp(signUpReqDto);
        return ApiResponse.createSuccess(result);
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<AuthenticationSuccessfulDto> login(final @Valid @RequestBody LoginReqDto loginReqDto)
        throws AuthenticationFailException, NonJoinUserException {
        return ApiResponse.createSuccess(new AuthenticationSuccessfulDto(userService.login(loginReqDto)));
    }

    @GetMapping("/getUser/{email}")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<GetUserResDto> getUser(@PathVariable("email") String email) throws DuplicateEmailException {
        return ApiResponse.createSuccess(userService.getUser(email));
    }
}
