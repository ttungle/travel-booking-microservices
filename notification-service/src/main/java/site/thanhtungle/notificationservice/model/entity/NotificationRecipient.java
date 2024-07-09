package site.thanhtungle.notificationservice.model.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "notification_recipient")
public class NotificationRecipient extends BaseEntity {

    @Column(name = "user_id")
    private String userId;

    @Column(name = "read")
    private Boolean read;

    @Column(name = "trash")
    private Boolean trash;

    @ManyToOne(fetch = FetchType.LAZY,
            cascade = {CascadeType.DETACH, CascadeType.MERGE,
            CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "notification_id")
    private Notification notification;
}
