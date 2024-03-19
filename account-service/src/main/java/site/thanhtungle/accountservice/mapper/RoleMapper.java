package site.thanhtungle.accountservice.mapper;

import org.keycloak.representations.idm.RoleRepresentation;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import site.thanhtungle.accountservice.model.dto.request.RoleRequestDTO;
import site.thanhtungle.accountservice.model.dto.response.RoleResponseDTO;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface RoleMapper {

    RoleRepresentation toRoleRepresentation(RoleRequestDTO roleRequestDTO);

    RoleResponseDTO toRoleResponseDTO(RoleRepresentation roleRepresentation);

    void updateRole(RoleRequestDTO roleRequestDTO, @MappingTarget RoleRepresentation roleRepresentation);
}
