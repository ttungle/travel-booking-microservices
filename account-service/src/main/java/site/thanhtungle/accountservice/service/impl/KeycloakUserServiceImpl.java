package site.thanhtungle.accountservice.service.impl;


import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import org.keycloak.admin.client.CreatedResponseUtil;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.*;
import org.keycloak.representations.idm.ClientRepresentation;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import site.thanhtungle.accountservice.mapper.UserMapper;
import site.thanhtungle.accountservice.model.dto.request.UserRequestDTO;
import site.thanhtungle.accountservice.model.dto.response.UserResponseDTO;
import site.thanhtungle.accountservice.service.KeycloakUserService;
import site.thanhtungle.commons.exception.CustomBadRequestException;

import java.util.*;

@RequiredArgsConstructor
@Service
public class KeycloakUserServiceImpl implements KeycloakUserService {

    private final Keycloak keycloak;
    private final UserMapper userMapper;
    @Value("${keycloak.realm}")
    private String realm;
    @Value("${keycloak.client-id}")
    private String clientId;

    @Override
    public UserResponseDTO createUser(UserRequestDTO userRequestDTO) {
        Response response = createKeycloakUser(userRequestDTO);
        if (!Objects.equals(201, response.getStatus())) {
            throw new CustomBadRequestException("Failed to create new user: " + response.getStatusInfo().getReasonPhrase());
        }
        return userMapper.toUserResponseDTO(userRequestDTO);
    }

    @Override
    public UserResponseDTO updateUser(UserRequestDTO userRequestDTO) {
        return null;
    }

    public Response createKeycloakUser(UserRequestDTO userRequestDTO) {
        if (userRequestDTO.getEmail() == null) throw new CustomBadRequestException("The email is required.");
        UsersResource usersResource = getUserResource();

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
        assignUserRole(response, userRequestDTO.getRole());
        return response;
    }

    private void assignUserRole(Response response, String role) {
        RealmResource realmResource = getRealmResource();
        UsersResource usersResource = getUserResource();

        String userId = CreatedResponseUtil.getCreatedId(response);
        UserResource currentUser = usersResource.get(userId);
        ClientRepresentation clientRepresentation = realmResource.clients().findByClientId(clientId).get(0);
        RoleRepresentation userRealmRole = realmResource.roles().get(role).toRepresentation();
        RoleRepresentation userClientRole = realmResource.clients().get(clientRepresentation.getId())
                .roles().get(role).toRepresentation();
        currentUser.roles().clientLevel(clientRepresentation.getId()).add(Collections.singletonList(userClientRole));
        currentUser.roles().realmLevel().add(Collections.singletonList(userRealmRole));
    }

    private RealmResource getRealmResource() { return keycloak.realm(realm); }

    private UsersResource getUserResource() { return getRealmResource().users(); }
}
