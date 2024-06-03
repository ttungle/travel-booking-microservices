package site.thanhtungle.commons.model.response.error;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public class ApiMultipleErrorResponse {

    private int status;
    private List<Map<String, String>> errors;
    private LocalDateTime timestamp;
    private String path;

    public ApiMultipleErrorResponse() {
        this.timestamp = LocalDateTime.now();
    }

    public ApiMultipleErrorResponse(int status, List<Map<String, String>> errors, String path) {
        this.status = status;
        this.errors = errors;
        this.timestamp = LocalDateTime.now();
        this.path = path;
    }
}
