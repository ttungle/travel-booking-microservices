package site.thanhtungle.notificationservice.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDTO {
    private String id;
    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private List<String> realmRoles;
    private Map<String, List<String>> clientRoles;
    private Boolean enabled;
    private Long createdTimestamp;
}
