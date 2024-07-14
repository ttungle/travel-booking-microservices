package site.thanhtungle.accountservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import site.thanhtungle.accountservice.model.dto.request.UserRequestDTO;
import site.thanhtungle.accountservice.model.dto.request.UserUpdateRequestDTO;
import site.thanhtungle.accountservice.model.dto.response.UserResponseDTO;
import site.thanhtungle.accountservice.service.KeycloakUserService;
import site.thanhtungle.commons.model.response.success.BaseApiResponse;
import site.thanhtungle.commons.model.response.success.PagingApiResponse;

import java.util.List;

@RestController
@RequestMapping("${api.url.user}")
@AllArgsConstructor
@SecurityRequirement(name = "BearerAuth")
public class UserController {

    private final KeycloakUserService keycloakUserService;

    @Operation(summary = "Create new user")
    @PostMapping
    public ResponseEntity<BaseApiResponse<UserResponseDTO>> createUer(@RequestBody UserRequestDTO userRequestDTO) {
        UserResponseDTO userResponseDTO = keycloakUserService.createUser(userRequestDTO);
        BaseApiResponse<UserResponseDTO> response = new BaseApiResponse<>(HttpStatus.CREATED.value(), userResponseDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "Update user")
    @PutMapping("/{id}")
    public ResponseEntity<BaseApiResponse<String>> updateUser(@PathVariable("id") String userId,
                                                              @RequestBody UserUpdateRequestDTO userUpdateRequestDTO) {
        keycloakUserService.updateUser(userId, userUpdateRequestDTO);
        BaseApiResponse<String> response = new BaseApiResponse<>(HttpStatus.OK.value(),
                "The user has been updated successfully.");
        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "Get user by id")
    @GetMapping("/{id}")
    public ResponseEntity<BaseApiResponse<UserResponseDTO>> getUser(@PathVariable("id") String userId) {
        UserResponseDTO responseDTO = keycloakUserService.getUser(userId);
        BaseApiResponse<UserResponseDTO> response = new BaseApiResponse<>(HttpStatus.OK.value(), responseDTO);
        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "Get all users with search and pagination")
    @GetMapping
    public ResponseEntity<PagingApiResponse<List<UserResponseDTO>>> getAllUsers(
            @RequestParam(name = "q", required = false) String search,
            @RequestParam(name = "page", defaultValue = "1", required = false) Integer page,
            @RequestParam(name = "pageSize", defaultValue = "25", required = false) Integer pageSize
    ) {
        return ResponseEntity.ok().body(keycloakUserService.getAllUsers(search, page, pageSize));
    }

    @Operation(summary = "Delete user by id")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable("id") String userId) {
        keycloakUserService.deleteUser(userId);
    }
}
