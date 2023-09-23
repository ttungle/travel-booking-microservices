package site.thanhtungle.commons.model.response.error;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BaseErrorResponse {

    private int status;
    private String message;
}
