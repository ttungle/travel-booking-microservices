package site.thanhtungle.accountservice.service;


import jakarta.ws.rs.core.Response;
import org.springframework.transaction.annotation.Transactional;
import site.thanhtungle.accountservice.model.dto.request.UserRequestDTO;
import site.thanhtungle.accountservice.model.dto.request.UserUpdateRequestDTO;
import site.thanhtungle.accountservice.model.dto.response.UserResponseDTO;
import site.thanhtungle.commons.model.response.success.PagingApiResponse;

import java.util.List;

@Transactional
public interface KeycloakUserService {

    /**
     * Create new Keycloak user
     *
     * @param userRequestDTO
     * @return UserResponseDTO
     * */
    UserResponseDTO createUser(UserRequestDTO userRequestDTO);

    /**
     * Update Keycloak user by Id
     *
     * @param userId
     * @param userUpdateRequestDTO
     * */
    void updateUser(String userId, UserUpdateRequestDTO userUpdateRequestDTO);

    /**
     * Get user by id
     *
     * @param userId
     * @return UserResponseDTO
     * */
    @Transactional(readOnly = true)
    UserResponseDTO getUser(String userId);

    /**
     * Get all users with search and pagination by username, email, lastName, firstName
     *
     * @param search
     * @param page
     * @param pageSize
     * @return PagingApiResponse<List<UserResponseDTO>>
     * */
    @Transactional(readOnly = true)
    PagingApiResponse<List<UserResponseDTO>> getAllUsers(String search, Integer page, Integer pageSize);

    /**
     * Delete user by id
     *
     * @param userId
     * */
    void deleteUser(String userId);

    /**
     * Create Keycloak user common method
     *
     * @param userRequestDTO
     * @return Response
     * */
    Response createKeycloakUser(UserRequestDTO userRequestDTO);
}
