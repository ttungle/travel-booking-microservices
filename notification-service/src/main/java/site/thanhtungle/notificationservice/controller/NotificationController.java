package site.thanhtungle.notificationservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
        return null;
    }

    @PutMapping("/read/{notificationId}")
    public ResponseEntity<BaseApiResponse<String>> toggleNotificationRead(Principal principal, @PathVariable("notificationId") Long notificationId) {
        return null;
    }

    @GetMapping("/unread/count")
    public ResponseEntity<BaseApiResponse<Long>> getUnreadNotificationCount() {
        return null;
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
