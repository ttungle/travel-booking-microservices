package site.thanhtungle.accountservice.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import site.thanhtungle.accountservice.model.dto.request.RegisterUserRequestDTO;
import site.thanhtungle.accountservice.model.dto.response.UserResponseDTO;
import site.thanhtungle.accountservice.service.AuthService;
import site.thanhtungle.commons.model.response.success.BaseApiResponse;

@RestController
@RequestMapping("${api.url.auth}")
@AllArgsConstructor
@Slf4j
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<BaseApiResponse<UserResponseDTO>> register(@RequestBody RegisterUserRequestDTO registerUserRequestDTO) {
        UserResponseDTO userResponseDTO = authService.registerUser(registerUserRequestDTO);
        BaseApiResponse<UserResponseDTO> response = new BaseApiResponse<>(HttpStatus.CREATED.value(), userResponseDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
