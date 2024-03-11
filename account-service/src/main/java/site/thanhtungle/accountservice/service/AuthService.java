package site.thanhtungle.accountservice.service;

import jakarta.transaction.Transactional;
import org.keycloak.representations.AccessTokenResponse;
import site.thanhtungle.accountservice.model.dto.request.LoginRequestDTO;
import site.thanhtungle.accountservice.model.dto.request.LogoutRequestDTO;
import site.thanhtungle.accountservice.model.dto.request.RegisterUserRequestDTO;
import site.thanhtungle.accountservice.model.dto.response.RegisterUserResponseDTO;

@Transactional
public interface AuthService {

    /**
     * Register new user
     *
     * @param registerUserRequestDTO - x-www-form-urlencoded contains firstname, lastname, email, password, passwordConfirm
     * @return UserResponseDTO
     * */
    RegisterUserResponseDTO registerUser(RegisterUserRequestDTO registerUserRequestDTO);

    /**
     * Login user
     *
     * @param loginRequestDTO
     * @return AccessTokenResponse
     * */
    AccessTokenResponse login(LoginRequestDTO loginRequestDTO);

    /**
     * Logout all session of a user, require ADMIN role
     *
     * @param userId Keycloak user UUID
     * */
    void logoutUserById(String userId);

    /**
     * Logout current session, require client_id, refresh_token
     *
     * @param logoutRequestDTO contains JSON client_id, refresh_token
     * */
    void logoutCurrentUser(LogoutRequestDTO logoutRequestDTO);
}
