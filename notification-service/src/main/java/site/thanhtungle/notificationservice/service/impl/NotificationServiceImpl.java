package site.thanhtungle.notificationservice.service.impl;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import site.thanhtungle.commons.constant.enums.ENotificationType;
import site.thanhtungle.commons.exception.CustomNotFoundException;
import site.thanhtungle.commons.model.criteria.BaseCriteria;
import site.thanhtungle.commons.model.response.success.PageInfo;
import site.thanhtungle.commons.model.response.success.PagingApiResponse;
import site.thanhtungle.commons.util.CommonPageUtil;
import site.thanhtungle.notificationservice.mapper.NotificationMapper;
import site.thanhtungle.notificationservice.model.dto.request.NotificationMessageDTO;
import site.thanhtungle.notificationservice.model.dto.response.NotificationResponseDTO;
import site.thanhtungle.notificationservice.model.entity.Notification;
import site.thanhtungle.notificationservice.model.entity.NotificationRecipient;
import site.thanhtungle.notificationservice.repository.NotificationRecipientRepository;
import site.thanhtungle.notificationservice.repository.NotificationRepository;
import site.thanhtungle.notificationservice.service.NotificationService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;
    private final NotificationMapper notificationMapper;
    private final SimpMessagingTemplate simpMessagingTemplate;
    private final NotificationRecipientRepository notificationRecipientRepository;
    @Value("${ws.topic.notification.user}")
    private String notificationUserTopic;
    @Value("${ws.topic.notification.all}")
    private String notificationAllTopic;

    @Override
    public void createNotification(@Valid NotificationMessageDTO notificationMessageDTO) {
        Notification notification = notificationMapper.toNotificationEntity(notificationMessageDTO);
        Notification updatedNotification = notificationRepository.save(notification);
        NotificationResponseDTO notificationResponseDTO = notificationMapper.toNotificationResponseDTO(updatedNotification);

        if (updatedNotification.getType() == ENotificationType.USER) {
            if (notificationMessageDTO.getRecipientIds().isEmpty()) return;
            notificationMessageDTO.getRecipientIds().forEach(recipientId -> {
                simpMessagingTemplate.convertAndSend(notificationUserTopic + recipientId, notificationResponseDTO);
                NotificationRecipient notificationRecipient = NotificationRecipient.builder()
                        .read(false)
                        .trash(false)
                        .userId(recipientId)
                        .notification(notification)
                        .build();
                notificationRecipientRepository.save(notificationRecipient);
            });
        } else {
            simpMessagingTemplate.convertAndSend(notificationAllTopic, notificationResponseDTO);
        }
    }

    @Override
    public void markAllNotificationAsRead(String userId) {
        Assert.notNull(userId, "userId cannot be null.");
        notificationRecipientRepository.updateAllNotificationAsRead(userId);
    }

    @Override
    public void toggleReadNotification(String userId, Long notificationId) {
        Assert.notNull(userId, "userId cannot be null.");
        Assert.notNull(notificationId, "notificationId cannot be null.");
        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new CustomNotFoundException("No notification found with that id."));

    }

    @Override
    public Long countUnreadNotification(String userId) {
        return null;
    }

    @Override
    public PagingApiResponse<List<NotificationResponseDTO>> getAllNotification(String userId, BaseCriteria baseCriteria) {
        PageRequest pageRequest = CommonPageUtil.getPageRequest(baseCriteria.getPage(),
                baseCriteria.getPageSize(), baseCriteria.getSort());
        Page<NotificationResponseDTO> notificationPage = notificationRepository
                .findAllNotificationByUserId(userId, pageRequest);
        return getListPagingApiResponse(baseCriteria, notificationPage);
    }

    @Override
    public PagingApiResponse<List<NotificationResponseDTO>> getUnreadNotification(String userId, BaseCriteria baseCriteria) {
        PageRequest pageRequest = CommonPageUtil.getPageRequest(baseCriteria.getPage(),
                baseCriteria.getPageSize(), baseCriteria.getSort());
        Page<NotificationResponseDTO> notificationPage = notificationRepository
                .findUnreadNotificationByUserId(userId, pageRequest);
        return getListPagingApiResponse(baseCriteria, notificationPage);
    }

    @NotNull
    private PagingApiResponse<List<NotificationResponseDTO>> getListPagingApiResponse(BaseCriteria baseCriteria, Page<NotificationResponseDTO> notificationPage) {
        List<NotificationResponseDTO> notificationList = notificationPage.getContent();
        PageInfo pageInfo = new PageInfo(baseCriteria.getPage(), baseCriteria.getPageSize(),
                notificationPage.getTotalElements(), notificationPage.getTotalPages());
        return new PagingApiResponse<>(HttpStatus.OK.value(), notificationList, pageInfo);
    }
}
