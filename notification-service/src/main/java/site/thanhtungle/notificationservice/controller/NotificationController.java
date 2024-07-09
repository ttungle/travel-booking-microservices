package site.thanhtungle.notificationservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import site.thanhtungle.commons.model.criteria.BaseCriteria;
import site.thanhtungle.commons.model.response.success.BaseApiResponse;
import site.thanhtungle.commons.model.response.success.PagingApiResponse;
import site.thanhtungle.notificationservice.model.dto.response.NotificationResponseDTO;
import site.thanhtungle.notificationservice.service.NotificationService;

import java.util.List;

@RestController
@RequestMapping("${api.url.notification}")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @PutMapping("/read/all/{userId}")
    public ResponseEntity<BaseApiResponse<String>> markAllNotificationAsRead(@PathVariable("userId") String userId) {
        return null;
    }

    @PutMapping("/read/{notificationId}")
    public ResponseEntity<BaseApiResponse<String>> toggleNotificationRead(@PathVariable("notificationId") Long notificationId) {
        return null;
    }

    @GetMapping("/unread/count")
    public ResponseEntity<BaseApiResponse<Long>> getUnreadNotificationCount() {
        return null;
    }

    @GetMapping("/{id}")
    ResponseEntity<PagingApiResponse<List<NotificationResponseDTO>>> getAllNotification(@PathVariable("id") String userId, @Valid BaseCriteria baseCriteria) {
        PagingApiResponse<List<NotificationResponseDTO>> response = notificationService.getAllNotification(userId, baseCriteria);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/unread/{id}/list")
    ResponseEntity<PagingApiResponse<List<NotificationResponseDTO>>> getUnreadNotification(@PathVariable("id") String userId, @Valid BaseCriteria baseCriteria) {
        PagingApiResponse<List<NotificationResponseDTO>> response = notificationService.getUnreadNotification(userId, baseCriteria);
        return ResponseEntity.ok().body(response);
    }
}
