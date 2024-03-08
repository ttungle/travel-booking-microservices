package site.thanhtungle.accountservice.service;

import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.Response;
import site.thanhtungle.accountservice.model.dto.request.UserRequestDTO;
import site.thanhtungle.accountservice.model.dto.response.UserResponseDTO;

@Transactional
public interface KeycloakUserService {

    UserResponseDTO createUser(UserRequestDTO userRequestDTO);

    UserResponseDTO updateUser(UserRequestDTO userRequestDTO);

    Response createKeycloakUser(UserRequestDTO userRequestDTO);
}
