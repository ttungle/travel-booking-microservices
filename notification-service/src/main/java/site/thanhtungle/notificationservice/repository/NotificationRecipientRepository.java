package site.thanhtungle.notificationservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import site.thanhtungle.notificationservice.model.entity.NotificationRecipient;

@Repository
public interface NotificationRecipientRepository extends JpaRepository<NotificationRecipient, Long> {

    @Modifying
    @Query("UPDATE NotificationRecipient nr SET nr.read = true WHERE nr.userId = :userId AND nr.read = false")
    void updateAllNotificationAsRead(String userId);
}