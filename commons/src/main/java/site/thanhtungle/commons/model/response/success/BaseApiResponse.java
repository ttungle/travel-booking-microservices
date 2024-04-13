package site.thanhtungle.commons.model.response.success;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BaseApiResponse<T> implements Serializable {

    private int status;
    private T data;
}
