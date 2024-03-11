package site.thanhtungle.accountservice.service.impl;

import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.AccessTokenResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import site.thanhtungle.accountservice.mapper.UserMapper;
import site.thanhtungle.accountservice.model.dto.request.*;
import site.thanhtungle.accountservice.model.dto.response.RegisterUserResponseDTO;
import site.thanhtungle.accountservice.service.AuthService;
import site.thanhtungle.accountservice.service.KeycloakUserService;
import site.thanhtungle.accountservice.service.rest.KeycloakApiClient;
import site.thanhtungle.commons.exception.CustomBadRequestException;

import java.util.Objects;

@RequiredArgsConstructor
@Service
public class AuthServiceImpl implements AuthService {

    private final UserMapper userMapper;
    private final KeycloakUserService keycloakUserService;
    private final KeycloakApiClient keycloakApiClient;
    private final Keycloak keycloak;
    @Value("${keycloak.server-url}")
    private String serverUrl;
    @Value("${keycloak.realm}")
    private String realm;
    @Value("${keycloak.client-id}")
    private String clientId;

    @Override
    public RegisterUserResponseDTO registerUser(RegisterUserRequestDTO registerUserRequestDTO) {
        if (!Objects.equals(registerUserRequestDTO.getPassword(), registerUserRequestDTO.getPasswordConfirm())) {
            throw new CustomBadRequestException("The retyped password does not match.");
        }

        UserRequestDTO userRequestDTO = userMapper.toUserRequestDTO(registerUserRequestDTO);
        userRequestDTO.setRole("user");
        Response response = keycloakUserService.createKeycloakUser(userRequestDTO);

        if (!Objects.equals(response.getStatus(), 201)) {
            throw new CustomBadRequestException("Failed to register user: " + response.getStatusInfo().getReasonPhrase());
        }

        LoginRequestDTO loginRequestDTO = new LoginRequestDTO(registerUserRequestDTO.getEmail(),
                registerUserRequestDTO.getPassword());

        AccessTokenResponse accessTokenResponse = login(loginRequestDTO);
        return userMapper.toRegisterUserResponseDTO(userRequestDTO, accessTokenResponse);
    }

    @Override
    public AccessTokenResponse login(LoginRequestDTO loginRequestDTO) {
        String username = loginRequestDTO.getUsername();
        String password = loginRequestDTO.getPassword();
        if (username == null || password == null) {
            throw new CustomBadRequestException("The username and password cannot be null.");
        }

        KeycloakTokenRequestDTO keycloakTokenRequestDTO = new KeycloakTokenRequestDTO(
                OAuth2Constants.PASSWORD,
                clientId,
                username,
                password
        );
        return keycloakApiClient.login(keycloakTokenRequestDTO);
    }

    @Override
    public void logoutUserById(String userId) {
        keycloakApiClient.logoutUserById(userId);
    }

    @Override
    public void logoutCurrentUser(LogoutRequestDTO logoutRequestDTO) {
        keycloakApiClient.logoutCurrentUser(logoutRequestDTO);
    }
}
