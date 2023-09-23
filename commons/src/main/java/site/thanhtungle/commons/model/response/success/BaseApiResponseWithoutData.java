package site.thanhtungle.commons.model.response.success;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BaseApiResponseWithoutData {

    private int status;
    private String message;
}
