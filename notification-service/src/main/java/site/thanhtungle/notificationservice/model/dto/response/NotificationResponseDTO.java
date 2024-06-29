package site.thanhtungle.notificationservice.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NotificationResponseDTO {

    private String title;
    private String content;
    private String createdDate;
}
