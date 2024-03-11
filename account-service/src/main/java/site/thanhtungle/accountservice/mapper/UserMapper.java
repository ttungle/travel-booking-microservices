package site.thanhtungle.accountservice.mapper;

import org.keycloak.representations.AccessTokenResponse;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import site.thanhtungle.accountservice.model.dto.request.RegisterUserRequestDTO;
import site.thanhtungle.accountservice.model.dto.request.UserRequestDTO;
import site.thanhtungle.accountservice.model.dto.response.RegisterUserResponseDTO;
import site.thanhtungle.accountservice.model.dto.response.UserResponseDTO;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UserMapper {

    UserResponseDTO toUserResponseDTO(UserRequestDTO userRequestDTO);

    RegisterUserResponseDTO toRegisterUserResponseDTO(UserRequestDTO userRequestDTO,
                                                      AccessTokenResponse accessTokenResponse);

    UserRequestDTO toUserRequestDTO(RegisterUserRequestDTO registerUserRequestDTO);
}
