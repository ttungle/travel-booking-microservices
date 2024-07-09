package site.thanhtungle.notificationservice.model.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import site.thanhtungle.commons.constant.enums.ENotificationType;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NotificationMessageDTO {

    @NotNull(message = "title cannot be null.")
    private String title;
    @NotNull(message = "content cannot be null.")
    private String content;
    private String url;
    private Set<String> recipientIds;
    @NotNull(message = "type cannot be null.")
    private ENotificationType type;
}