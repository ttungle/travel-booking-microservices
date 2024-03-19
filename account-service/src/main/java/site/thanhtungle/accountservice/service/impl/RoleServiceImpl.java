package site.thanhtungle.accountservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.RoleResource;
import org.keycloak.admin.client.resource.RolesResource;
import org.keycloak.representations.idm.RoleRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import site.thanhtungle.accountservice.mapper.RoleMapper;
import site.thanhtungle.accountservice.model.dto.request.RoleRequestDTO;
import site.thanhtungle.accountservice.model.dto.response.RoleResponseDTO;
import site.thanhtungle.accountservice.service.RoleService;
import site.thanhtungle.commons.exception.CustomBadRequestException;
import site.thanhtungle.commons.exception.CustomNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class RoleServiceImpl implements RoleService {

    private final Keycloak keycloak;
    private final RoleMapper roleMapper;
    @Value("${keycloak.realm}")
    private String realm;

    @Override
    public RoleResponseDTO createRole(RoleRequestDTO roleRequestDTO) {
        if(roleRequestDTO.getName() == null) throw new CustomBadRequestException("Role name cannot be null.");
        try {
            RolesResource rolesResource = getRolesResource();
            rolesResource.create(roleMapper.toRoleRepresentation(roleRequestDTO));

            RoleResource roleResource = rolesResource.get(roleRequestDTO.getName());
            RoleRepresentation roleRepresentation = roleResource.toRepresentation();
            if (roleRepresentation.getId() == null) throw new CustomBadRequestException("Failed to create new role.");
            return roleMapper.toRoleResponseDTO(roleRepresentation);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new CustomBadRequestException("Failed to create new role by error: " + e.getLocalizedMessage());
        }
    }

    @Override
    public void updateRole(String roleName, RoleRequestDTO roleRequestDTO) {
        try {
            RolesResource rolesResource = getRolesResource();
            RoleResource roleResource = rolesResource.get(roleName);
            RoleRepresentation roleRepresentation = roleResource.toRepresentation();
            roleMapper.updateRole(roleRequestDTO, roleRepresentation);
            roleResource.update(roleRepresentation);
        } catch (Exception e) {
            log.error(e.getLocalizedMessage());
            throw new CustomBadRequestException("Failed to update role by error: " + e.getLocalizedMessage());
        }
    }

    @Override
    public RoleResponseDTO getRole(String roleName) {
        if (roleName == null) throw new CustomBadRequestException("Role name cannot be null.");
        try {
            RolesResource rolesResource = getRolesResource();
            RoleResource roleResource = rolesResource.get(roleName);
            return roleMapper.toRoleResponseDTO(roleResource.toRepresentation());
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new CustomNotFoundException("No role found with that role name by error: " + e.getLocalizedMessage());
        }
    }

    @Override
    public List<RoleResponseDTO> getAllRoles(String search, Integer page, Integer pageSize) {
        if (page == null || pageSize == null) throw new CustomBadRequestException("page or pageSize cannot be null.");
        try {
            Integer skip = pageSize * (page - 1);
            RolesResource rolesResource = getRolesResource();
            List<RoleRepresentation> roleRepresentationList = rolesResource.list(search, skip, pageSize);
            return roleRepresentationList.stream()
                    .map(roleMapper::toRoleResponseDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new CustomNotFoundException("User cannot be found by error: " + e.getLocalizedMessage());
        }
    }

    @Override
    public void deleteRole(String roleName) {
        if (roleName == null) throw new CustomBadRequestException("Role name cannot be null.");
        try {
            RolesResource rolesResource = getRolesResource();
            RoleRepresentation roleRepresentation = rolesResource.get(roleName).toRepresentation();
            if (roleRepresentation.getId() == null ) throw new CustomBadRequestException("No role found with the role name.");
            rolesResource.deleteRole(roleName);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new CustomNotFoundException("Failed to delete role by error : " + e.getLocalizedMessage());
        }
    }

    private RealmResource getRealmResource() { return keycloak.realm(realm); }

    private RolesResource getRolesResource() { return getRealmResource().roles(); }
}
