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
 * */
@Configuration
@EnableWebFluxSecurity
public class SecurityConfiguration {

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        http.csrf(ServerHttpSecurity.CsrfSpec::disable)
                .authorizeExchange(exchange -> exchange
                        .pathMatchers(HttpMethod.GET, "/api/v1/tours/**")
                        .permitAll()
                        .pathMatchers("/eureka/**")
                        .permitAll()
                        .anyExchange()
                        .authenticated()
                )
                .oauth2ResourceServer((oauth2) -> oauth2
                .jwt(Customizer.withDefaults()));
        return http.build();
    }
}
