package site.thanhtungle.commons.model.response;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BaseApiResponse<T> {

    private int status;
    private T data;
}
