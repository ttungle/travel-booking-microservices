package site.thanhtungle.notificationservice.mapper;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import site.thanhtungle.notificationservice.model.dto.request.NotificationMessageDTO;
import site.thanhtungle.notificationservice.model.dto.response.NotificationResponseDTO;
import site.thanhtungle.notificationservice.model.entity.Notification;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        injectionStrategy = InjectionStrategy.FIELD
)
public abstract class NotificationMapper {

    @Mapping(target = "notificationRecipients", ignore = true)
    public abstract Notification toNotificationEntity(NotificationMessageDTO notificationMessageDTO);

    public abstract NotificationResponseDTO toNotificationResponseDTO(Notification notification);
}
