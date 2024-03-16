package site.thanhtungle.accountservice.service.impl;


import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.CreatedResponseUtil;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.*;
import org.keycloak.representations.idm.ClientRepresentation;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import site.thanhtungle.accountservice.mapper.UserMapper;
import site.thanhtungle.accountservice.model.dto.request.UserRequestDTO;
import site.thanhtungle.accountservice.model.dto.request.UserUpdateRequestDTO;
import site.thanhtungle.accountservice.model.dto.response.UserResponseDTO;
import site.thanhtungle.accountservice.service.KeycloakUserService;
import site.thanhtungle.commons.exception.CustomBadRequestException;
import site.thanhtungle.commons.exception.CustomNotFoundException;
import site.thanhtungle.commons.model.response.success.PageInfo;
import site.thanhtungle.commons.model.response.success.PagingApiResponse;

import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Slf4j
public class KeycloakUserServiceImpl implements KeycloakUserService {

    private final Keycloak keycloak;
    private final UserMapper userMapper;
    @Value("${keycloak.realm}")
    private String realm;
    @Value("${keycloak.client-id}")
    private String clientId;

    @Override
    public UserResponseDTO createUser(UserRequestDTO userRequestDTO) {
        try {
            Response response = createKeycloakUser(userRequestDTO);
            if (!Objects.equals(201, response.getStatus())) {
                throw new CustomBadRequestException("Failed to create new user: " + response.getStatusInfo().getReasonPhrase());
            }
            UserResource currentUser = getCurrentUser(response);
            UserRepresentation userRepresentation = currentUser.toRepresentation();
            return userMapper.toUserResponseDTO(userRepresentation);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new CustomBadRequestException("Cannot create user by error : " + e.getLocalizedMessage());
        }
    }

    @Override
    public void updateUser(String userId, UserUpdateRequestDTO userUpdateRequestDTO) {
        if (userId == null) throw new CustomBadRequestException("User id cannot be null.");
        try {
            UsersResource usersResource = getUsersResource();
            UserResource userResource = usersResource.get(userId);
            UserRepresentation updatedUser = userMapper.updateUser(userUpdateRequestDTO, userResource.toRepresentation());
            userResource.update(updatedUser);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new CustomBadRequestException("Failed to update user by error: " + e.getLocalizedMessage());
        }
    }

    @Override
    public UserResponseDTO getUser(String userId) {
        if (userId == null) throw new CustomBadRequestException("User id cannot be null.");
        try {
            UsersResource usersResource = getUsersResource();
            UserRepresentation userRepresentation = usersResource.get(userId).toRepresentation();
            return userMapper.toUserResponseDTO(userRepresentation);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new CustomNotFoundException("User cannot be found by error: " + e.getLocalizedMessage());
        }
    }

    @Override
    public PagingApiResponse<List<UserResponseDTO>> getAllUsers(String search, Integer page, Integer pageSize) {
        if (page == null || pageSize == null) throw new CustomBadRequestException("page or pageSize cannot be null.");
        try {
            Integer skip = pageSize * (page - 1);
            UsersResource usersResource = getUsersResource();
            List<UserRepresentation> userRepresentationList = usersResource.search(search, skip, pageSize);
            Integer totalCount = usersResource.count(search);
            PageInfo pageInfo = new PageInfo(page, pageSize, Long.parseLong(totalCount.toString()));
            List<UserResponseDTO> userResponseDTOList = userRepresentationList.stream()
                    .map(userMapper::toUserResponseDTO)
                    .collect(Collectors.toList());
            return new PagingApiResponse<>(HttpStatus.OK.value(), userResponseDTOList, pageInfo);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new CustomNotFoundException("User cannot be found by error: " + e.getLocalizedMessage());
        }

    }

    @Override
    public void deleteUser(String userId) {
        if (userId == null) throw new CustomBadRequestException("User id cannot be null.");

        try {
            UsersResource usersResource = getUsersResource();
            usersResource.delete(userId);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new CustomBadRequestException("Cannot delete user by error: " + e.getLocalizedMessage());
        }

    }

    public Response createKeycloakUser(UserRequestDTO userRequestDTO) {
        if (userRequestDTO.getEmail() == null) throw new CustomBadRequestException("The email is required.");
        UsersResource usersResource = getUsersResource();

        // Define user
        UserRepresentation userRepresentation = new UserRepresentation();
        userRepresentation.setEnabled(true);
        userRepresentation.setEmail(userRequestDTO.getEmail());
        if (userRequestDTO.getFirstName() != null ) userRepresentation.setFirstName(userRequestDTO.getFirstName());
        if (userRequestDTO.getLastName() != null ) userRepresentation.setLastName(userRequestDTO.getLastName());

        // Define password credential
        CredentialRepresentation credentialRepresentation = new CredentialRepresentation();
        credentialRepresentation.setTemporary(false);
        credentialRepresentation.setType(CredentialRepresentation.PASSWORD);
        credentialRepresentation.setValue(userRequestDTO.getPassword());

        // Set password credential
        List<CredentialRepresentation> credentials = new ArrayList<>();
        credentials.add(credentialRepresentation);
        userRepresentation.setCredentials(credentials);

        // Create user
        Response response = usersResource.create(userRepresentation);

        // Assign roles
        UserResource currentUser = getCurrentUser(response);
        assignUserRole(currentUser, userRequestDTO.getRole());
        return response;
    }

    private void assignUserRole(UserResource currentUser, String role) {
        RealmResource realmResource = getRealmResource();
        ClientRepresentation clientRepresentation = realmResource.clients().findByClientId(clientId).get(0);
        RoleRepresentation userRealmRole = realmResource.roles().get(role).toRepresentation();
        RoleRepresentation userClientRole = realmResource.clients().get(clientRepresentation.getId())
                .roles().get(role).toRepresentation();
        currentUser.roles().clientLevel(clientRepresentation.getId()).add(Collections.singletonList(userClientRole));
        currentUser.roles().realmLevel().add(Collections.singletonList(userRealmRole));
    }

    private RealmResource getRealmResource() { return keycloak.realm(realm); }

    private UsersResource getUsersResource() { return getRealmResource().users(); }

    private UserResource getCurrentUser(Response response) {
        UsersResource usersResource = getUsersResource();
        String userId = CreatedResponseUtil.getCreatedId(response);
        return usersResource.get(userId);
    }
}
