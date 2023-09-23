package site.thanhtungle.commons.model.response.success;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BaseApiResponse<T> {

    private int status;
    private T data;
}
