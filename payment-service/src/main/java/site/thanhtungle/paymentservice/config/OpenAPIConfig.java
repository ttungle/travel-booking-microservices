package site.thanhtungle.paymentservice.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;

@OpenAPIDefinition(
        info = @Info(
                title = "Payment Service API Documentation",
                description = """
                        The Payment Service API facilitates the processing of payments for bookings within the
                        Travel Booking application.
                        """,
                version = "1.0")
)
@SecurityScheme(name = "BearerAuth", type = SecuritySchemeType.HTTP, scheme = "bearer", bearerFormat = "JWT",
        description = "Bearer token for authentication.")
public class OpenAPIConfig {
}
