package site.thanhtungle.accountservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import site.thanhtungle.accountservice.model.dto.request.RoleRequestDTO;
import site.thanhtungle.accountservice.model.dto.response.RoleResponseDTO;
import site.thanhtungle.accountservice.service.RoleService;
import site.thanhtungle.commons.model.response.success.BaseApiResponse;

import java.util.List;

@RestController
@RequestMapping("${api.url.role}")
@AllArgsConstructor
@SecurityRequirement(name = "BearerAuth")
public class RoleController {

    private final RoleService roleService;

    @Operation(summary = "Create new role")
    @PostMapping
    public ResponseEntity<BaseApiResponse<RoleResponseDTO>> createRole(@RequestBody RoleRequestDTO roleRequestDTO) {
        RoleResponseDTO roleResponseDTO = roleService.createRole(roleRequestDTO);
        BaseApiResponse<RoleResponseDTO> response = new BaseApiResponse<>(HttpStatus.CREATED.value(), roleResponseDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "Update role")
    @PutMapping("/{roleName}")
    public ResponseEntity<BaseApiResponse<String>> updateRole(@PathVariable("roleName") String roleName, @RequestBody RoleRequestDTO roleRequestDTO) {
        roleService.updateRole(roleName, roleRequestDTO);
        BaseApiResponse<String> response = new BaseApiResponse<>(HttpStatus.OK.value(), "Role has been updated successfully");
        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "Get role by name")
    @GetMapping("/{roleName}")
    public ResponseEntity<?> getRole(@PathVariable("roleName") String roleName) {
        RoleResponseDTO roleResponseDTO = roleService.getRole(roleName);
        BaseApiResponse<RoleResponseDTO> response = new BaseApiResponse<>(HttpStatus.OK.value(), roleResponseDTO);
        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "Get all roles with search and pagination")
    @GetMapping
    public ResponseEntity<BaseApiResponse<List<RoleResponseDTO>>> getAllRoles(
            @RequestParam(name = "q", required = false) String search,
            @RequestParam(name = "page", defaultValue = "1", required = false) Integer page,
            @RequestParam(name = "pageSize", defaultValue = "25", required = false) Integer pageSize
    ) {
        List<RoleResponseDTO> roleResponseDTOList = roleService.getAllRoles(search, page, pageSize);
        BaseApiResponse<List<RoleResponseDTO>> response = new BaseApiResponse<>(HttpStatus.OK.value(), roleResponseDTOList);
        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "Delete roles by name")
    @DeleteMapping("/{roleName}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteRole(@PathVariable("roleName") String roleName) {
        roleService.deleteRole(roleName);
    }
}
