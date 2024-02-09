package site.thanhtungle.tourservice.util.converter;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.stereotype.Component;
import reactor.util.annotation.NonNull;
import site.thanhtungle.commons.util.SecurityUtil;

import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
@Getter
@Setter
/**
 * Custom JwtGrantedAuthoritiesConverter to extract the role from Keycloak
 * */
public class JwtAuthoritiesConverter implements Converter<Jwt, Collection<GrantedAuthority>> {
    private final JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter =
            new JwtGrantedAuthoritiesConverter();
    @Value("${jwt.auth.converter.resource-id}")
    private String resourceId;

    @Override
    public Collection<GrantedAuthority> convert(@NonNull Jwt jwt) {
        return Stream.concat(
                jwtGrantedAuthoritiesConverter.convert(jwt).stream(),
                SecurityUtil.extractResourceRoles(jwt, resourceId).stream()
        ).collect(Collectors.toSet());
    }
}
