package site.thanhtungle.tourservice.model.dto.request.notification;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import site.thanhtungle.commons.constant.enums.ENotificationType;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
public class NotificationMessageDTO {

    private String title;
    private String content;
    private String url;
    private Set<String> recipientIds;
    private ENotificationType type;
}


