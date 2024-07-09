package site.thanhtungle.notificationservice.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import site.thanhtungle.commons.constant.enums.ENotificationType;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NotificationResponseDTO {

    private String title;
    private String content;
    private String url;
    private ENotificationType type;
    private Boolean read;
    private Boolean trash;
    private Instant createdAt;
    private Instant updatedAt;
}
