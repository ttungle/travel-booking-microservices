package site.thanhtungle.notificationservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import site.thanhtungle.commons.model.criteria.BaseCriteria;
import site.thanhtungle.commons.model.response.success.BaseApiResponse;
import site.thanhtungle.commons.model.response.success.PagingApiResponse;
import site.thanhtungle.notificationservice.model.dto.response.NotificationResponseDTO;
import site.thanhtungle.notificationservice.service.NotificationService;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("${api.url.notification}")
@RequiredArgsConstructor
@Slf4j
public class NotificationController {

    private final NotificationService notificationService;

    @PutMapping("/read/all")
    public ResponseEntity<BaseApiResponse<String>> markAllNotificationAsRead(Principal principal) {
        notificationService.markAllNotificationAsRead(principal.getName());
        BaseApiResponse<String> response = new BaseApiResponse<>(HttpStatus.OK.value(),
                "All notifications have been marked as read successfully.");
        return ResponseEntity.ok().body(response);
    }

    @PutMapping("/read/{notificationId}")
    public ResponseEntity<BaseApiResponse<String>> toggleNotificationRead(Principal principal, @PathVariable("notificationId") Long notificationId) {
        notificationService.toggleReadNotification(principal.getName(), notificationId);
        BaseApiResponse<String> response = new BaseApiResponse<>(HttpStatus.OK.value(),
                "Notification read status has been toggled successfully.");
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/unread/count")
    public ResponseEntity<BaseApiResponse<Long>> getUnreadNotificationCount(Principal principal) {
        Long count = notificationService.countUnreadNotification(principal.getName());
        BaseApiResponse<Long> response = new BaseApiResponse<>(HttpStatus.OK.value(), count);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping
    ResponseEntity<PagingApiResponse<List<NotificationResponseDTO>>> getAllNotification(Principal principal, @Valid BaseCriteria baseCriteria) {
        PagingApiResponse<List<NotificationResponseDTO>> response = notificationService.getAllNotification(principal.getName(), baseCriteria);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/unread/list")
    ResponseEntity<PagingApiResponse<List<NotificationResponseDTO>>> getUnreadNotification(Principal principal, @Valid BaseCriteria baseCriteria) {
        PagingApiResponse<List<NotificationResponseDTO>> response = notificationService.getUnreadNotification(principal.getName(), baseCriteria);
        return ResponseEntity.ok().body(response);
    }
}
