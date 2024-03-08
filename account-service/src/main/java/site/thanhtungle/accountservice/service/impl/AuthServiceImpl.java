package site.thanhtungle.accountservice.service.impl;

import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import site.thanhtungle.accountservice.mapper.UserMapper;
import site.thanhtungle.accountservice.model.dto.request.RegisterUserRequestDTO;
import site.thanhtungle.accountservice.model.dto.request.UserRequestDTO;
import site.thanhtungle.accountservice.model.dto.response.UserResponseDTO;
import site.thanhtungle.accountservice.service.AuthService;
import site.thanhtungle.accountservice.service.KeycloakUserService;
import site.thanhtungle.commons.exception.CustomBadRequestException;

import java.util.Objects;

@RequiredArgsConstructor
@Service
public class AuthServiceImpl implements AuthService {

    private final UserMapper userMapper;

    private final KeycloakUserService keycloakUserService;

    @Override
    public UserResponseDTO registerUser(RegisterUserRequestDTO registerUserRequestDTO) {
        if (!Objects.equals(registerUserRequestDTO.getPassword(), registerUserRequestDTO.getPasswordConfirm())) {
            throw new CustomBadRequestException("The retyped password does not match.");
        }

        UserRequestDTO userRequestDTO = userMapper.toUserRequestDTO(registerUserRequestDTO);
        userRequestDTO.setRole("user");
        Response response = keycloakUserService.createKeycloakUser(userRequestDTO);

        if (!Objects.equals(response.getStatus(), 201)) {
            throw new CustomBadRequestException("Failed to register user: " + response.getStatusInfo().getReasonPhrase());
        }
        return userMapper.toUserResponseDTO(userRequestDTO);
    }
}
