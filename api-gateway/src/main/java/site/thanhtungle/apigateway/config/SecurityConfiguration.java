package site.thanhtungle.apigateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

/**
 * Spring web flux security configuration for API Gateway
 */
@Configuration
@EnableWebFluxSecurity
public class SecurityConfiguration {

    private final static String[] AUTH_WHITELIST = {"/eureka/**", "/api/v1/auth/**", "/ws-notification/**",
            "/webjars/**", "/v3/api-docs/**", "/tour-service/v3/api-docs", "/review-service/v3/api-docs",
            "/payment-service/v3/api-docs", "/notification-service/v3/api-docs", "/inventory-service/v3/api-docs",
            "/booking-service/v3/api-docs", "/account-service/v3/api-docs"};

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        http.csrf(ServerHttpSecurity.CsrfSpec::disable)
                .authorizeExchange(exchange -> exchange
                        .pathMatchers(HttpMethod.GET, "/api/v1/tours/**").permitAll()
                        .pathMatchers(AUTH_WHITELIST).permitAll()
                        .anyExchange().authenticated()
                )
                .oauth2ResourceServer((oauth2) -> oauth2.jwt(Customizer.withDefaults()));
        return http.build();
    }
}
