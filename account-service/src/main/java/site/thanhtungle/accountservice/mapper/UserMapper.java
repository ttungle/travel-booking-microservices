package site.thanhtungle.accountservice.mapper;

import org.keycloak.representations.AccessTokenResponse;
import org.keycloak.representations.idm.UserRepresentation;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import site.thanhtungle.accountservice.model.dto.request.RegisterUserRequestDTO;
import site.thanhtungle.accountservice.model.dto.request.UserRequestDTO;
import site.thanhtungle.accountservice.model.dto.request.UserUpdateRequestDTO;
import site.thanhtungle.accountservice.model.dto.response.RegisterUserResponseDTO;
import site.thanhtungle.accountservice.model.dto.response.UserResponseDTO;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UserMapper {

    UserResponseDTO toUserResponseDTO(UserRepresentation userRepresentation);

    RegisterUserResponseDTO toRegisterUserResponseDTO(UserRequestDTO userRequestDTO,
                                                      AccessTokenResponse accessTokenResponse);

    UserRequestDTO toUserRequestDTO(RegisterUserRequestDTO registerUserRequestDTO);

    UserRepresentation updateUser(UserUpdateRequestDTO userUpdateRequestDTO, @MappingTarget UserRepresentation userRepresentation);
}
