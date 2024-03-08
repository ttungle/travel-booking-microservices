package site.thanhtungle.accountservice.service;

import jakarta.transaction.Transactional;
import site.thanhtungle.accountservice.model.dto.request.RegisterUserRequestDTO;
import site.thanhtungle.accountservice.model.dto.response.UserResponseDTO;

@Transactional
public interface AuthService {

    UserResponseDTO registerUser(RegisterUserRequestDTO registerUserRequestDTO);
}
