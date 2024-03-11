package site.thanhtungle.accountservice.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.keycloak.representations.AccessTokenResponse;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterUserResponseDTO extends AccessTokenResponse {

    private String firstName;
    private String lastName;
    private String email;
}
