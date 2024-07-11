package site.thanhtungle.notificationservice.service.rest;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import site.thanhtungle.commons.model.response.success.PagingApiResponse;
import site.thanhtungle.notificationservice.model.dto.response.UserResponseDTO;

import java.util.List;

@FeignClient(name = "ACCOUNT-SERVICE", path = "/api/v1/users")
public interface AccountApiClient {

    @GetMapping
    ResponseEntity<PagingApiResponse<List<UserResponseDTO>>> getAllUsers(
            @RequestParam(name = "q", required = false) String search,
            @RequestParam(name = "page", defaultValue = "1", required = false) Integer page,
            @RequestParam(name = "pageSize", defaultValue = "25", required = false) Integer pageSize
    );
}
