package site.thanhtungle.accountservice.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.representations.AccessTokenResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import site.thanhtungle.accountservice.model.dto.request.LoginRequestDTO;
import site.thanhtungle.accountservice.model.dto.request.LogoutRequestDTO;
import site.thanhtungle.accountservice.model.dto.request.RegisterUserRequestDTO;
import site.thanhtungle.accountservice.model.dto.response.RegisterUserResponseDTO;
import site.thanhtungle.accountservice.service.AuthService;
import site.thanhtungle.commons.model.response.success.BaseApiResponse;

@RestController
@RequestMapping("${api.url.auth}")
@AllArgsConstructor
@Slf4j
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<BaseApiResponse<RegisterUserResponseDTO>> register(@RequestBody RegisterUserRequestDTO registerUserRequestDTO) {
        RegisterUserResponseDTO registerUserResponseDTO = authService.registerUser(registerUserRequestDTO);
        BaseApiResponse<RegisterUserResponseDTO> response = new BaseApiResponse<>(HttpStatus.CREATED.value(), registerUserResponseDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<BaseApiResponse<AccessTokenResponse>> login(@RequestBody LoginRequestDTO loginRequestDTO) {
        AccessTokenResponse accessTokenResponse = authService.login(loginRequestDTO);
        BaseApiResponse<AccessTokenResponse> response = new BaseApiResponse<>(HttpStatus.OK.value(), accessTokenResponse);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/logout/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void logoutUserById(@PathVariable("id") String userId) {
        authService.logoutUserById(userId);
    }

    @PostMapping(value = "/logout")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void logoutCurrentUser(@RequestBody LogoutRequestDTO logoutRequestDTO) {
        authService.logoutCurrentUser(logoutRequestDTO);
    }
}
