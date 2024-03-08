package site.thanhtungle.accountservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import site.thanhtungle.accountservice.model.dto.request.RegisterUserRequestDTO;
import site.thanhtungle.accountservice.model.dto.request.UserRequestDTO;
import site.thanhtungle.accountservice.model.dto.response.UserResponseDTO;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UserMapper {

    UserResponseDTO toUserResponseDTO(UserRequestDTO userRequestDTO);

    UserRequestDTO toUserRequestDTO(RegisterUserRequestDTO registerUserRequestDTO);
}
