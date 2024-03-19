package site.thanhtungle.accountservice.service;


import org.springframework.transaction.annotation.Transactional;
import site.thanhtungle.accountservice.model.dto.request.RoleRequestDTO;
import site.thanhtungle.accountservice.model.dto.response.RoleResponseDTO;

import java.util.List;

@Transactional
public interface RoleService {

    RoleResponseDTO createRole(RoleRequestDTO roleRequestDTO);

    void updateRole(String roleName, RoleRequestDTO roleRequestDTO);

    @Transactional(readOnly = true)
    RoleResponseDTO getRole(String roleName);

    @Transactional(readOnly = true)
    List<RoleResponseDTO> getAllRoles(String search, Integer page, Integer pageSize);

    void deleteRole(String roleName);
}
