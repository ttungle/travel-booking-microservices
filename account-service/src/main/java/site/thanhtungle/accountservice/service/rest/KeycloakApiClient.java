package site.thanhtungle.accountservice.service.rest;

import org.keycloak.representations.AccessTokenResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import site.thanhtungle.accountservice.model.dto.request.KeycloakTokenRequestDTO;
import site.thanhtungle.accountservice.model.dto.request.LogoutRequestDTO;

@FeignClient(url = "${keycloak.server-url}", name = "keycloakApiClient")
public interface KeycloakApiClient {

    @PostMapping(
            value = "${feign.url.keycloak.token}",
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE
    )
    AccessTokenResponse login(@RequestBody KeycloakTokenRequestDTO keycloakTokenRequestDTO);

    @PostMapping(value = "${feign.url.keycloak.logout-all-session}")
    void logoutUserById(@PathVariable("id") String userId);

    @PostMapping(
            value = "${feign.url.keycloak.logout-current-user}",
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE
    )
    void logoutCurrentUser(@RequestBody LogoutRequestDTO logoutRequestDTO);
}
