package site.thanhtungle.commons.model.response.error;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ApiErrorResponse extends BaseErrorResponse {

    private LocalDateTime timestamp;
    private String path;

    public ApiErrorResponse() {
        this.timestamp = LocalDateTime.now();
    }

    public ApiErrorResponse(int status, String message, String path) {
        super(status, message);
        this.timestamp = LocalDateTime.now();
        this.path = path;
    }
}
