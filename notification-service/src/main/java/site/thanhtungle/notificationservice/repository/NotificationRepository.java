package site.thanhtungle.notificationservice.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import site.thanhtungle.notificationservice.model.dto.response.NotificationResponseDTO;
import site.thanhtungle.notificationservice.model.entity.Notification;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {

    @Query(""" 
            SELECT new site.thanhtungle.notificationservice.model.dto.response.NotificationResponseDTO(n.id, n.title,
            n.content, n.url, n.type, nr.read, nr.trash, n.createdAt, n.updatedAt)
            FROM Notification n
            LEFT JOIN NotificationRecipient nr ON nr.notification.id = n.id
            WHERE nr.userId = :userId
    """)
    Page<NotificationResponseDTO> findAllNotificationByUserId(@Param("userId") String userId, Pageable pageable);

    @Query("""
            SELECT new site.thanhtungle.notificationservice.model.dto.response.NotificationResponseDTO(n.id, n.title,
            n.content, n.url, n.type, nr.read, nr.trash, n.createdAt, n.updatedAt)
            FROM Notification n
            LEFT JOIN NotificationRecipient nr ON nr.notification.id = n.id
            WHERE nr.read = false AND nr.userId = :userId
    """)
    Page<NotificationResponseDTO> findUnreadNotificationByUserId(@Param("userId") String userId, Pageable pageable);
}