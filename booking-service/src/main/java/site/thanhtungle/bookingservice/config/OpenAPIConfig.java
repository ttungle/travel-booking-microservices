package site.thanhtungle.bookingservice.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;

@OpenAPIDefinition(
        info = @Info(
                title = "Booking Service API Documentation",
                description = """
                        The Booking Service API provides essential functionality for booking tours within the
                         Travel Booking application. This service offers endpoints for creating, managing, and
                         retrieving bookings, ensuring a streamlined and efficient booking process for users.
                        """,
                version = "1.0")
)
@SecurityScheme(name = "BearerAuth", type = SecuritySchemeType.HTTP, scheme = "bearer", bearerFormat = "JWT",
        description = "Bearer token for authentication.")
public class OpenAPIConfig {
}
