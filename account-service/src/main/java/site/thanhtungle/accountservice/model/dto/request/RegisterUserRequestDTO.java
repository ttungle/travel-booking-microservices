package site.thanhtungle.accountservice.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterUserRequestDTO {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String passwordConfirm;
}
