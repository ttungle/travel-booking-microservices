package site.thanhtungle.commons.util;

import lombok.experimental.UtilityClass;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@UtilityClass
public class SecurityUtil {

    /**
     * Get nested role from Keycloak roles object and convert to Spring role.
     * */
    public Collection<GrantedAuthority> extractResourceRoles(Jwt jwt,String resourceId) {
        Map<String, Object> resourceAccess;
        Map<String, Object> resource;
        Collection<String> resourceRoles;

        if (jwt.getClaimAsMap("resource_access") == null) {
            return Set.of();
        }
        resourceAccess = jwt.getClaimAsMap("resource_access");

        if (resourceAccess.get(resourceId) == null) {
            return Set.of();
        }
        resource = (Map<String, Object>) resourceAccess.get(resourceId);

        resourceRoles = (Collection<String>) resource.get("roles");
        return resourceRoles
                .stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                .collect(Collectors.toSet());
    }
}
