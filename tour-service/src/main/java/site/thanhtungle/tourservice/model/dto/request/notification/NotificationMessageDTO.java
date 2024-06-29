package site.thanhtungle.tourservice.model.dto.request.notification;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class NotificationMessageDTO {

    private String title;
    private String content;
}
