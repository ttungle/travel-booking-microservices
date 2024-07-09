package site.thanhtungle.notificationservice.service;

import org.springframework.transaction.annotation.Transactional;
import site.thanhtungle.commons.model.criteria.BaseCriteria;
import site.thanhtungle.commons.model.response.success.PagingApiResponse;
import site.thanhtungle.notificationservice.model.dto.request.NotificationMessageDTO;
import site.thanhtungle.notificationservice.model.dto.response.NotificationResponseDTO;

import java.util.List;

@Transactional
public interface NotificationService {

    void createNotification(NotificationMessageDTO notificationMessageDTO);

    void markAllNotificationAsRead(String userId);

    void toggleReadNotification(String userId, Long notificationId);

    Long countUnreadNotification(String userId);

    @Transactional(readOnly = true)
    PagingApiResponse<List<NotificationResponseDTO>> getAllNotification(String userId, BaseCriteria baseCriteria);

    @Transactional(readOnly = true)
    PagingApiResponse<List<NotificationResponseDTO>> getUnreadNotification(String userId, BaseCriteria baseCriteria);
}
