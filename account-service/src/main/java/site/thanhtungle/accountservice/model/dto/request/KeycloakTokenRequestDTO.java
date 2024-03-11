package site.thanhtungle.accountservice.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class KeycloakTokenRequestDTO {

    private String grant_type;
    private String client_id;
    private String username;
    private String password;
}
