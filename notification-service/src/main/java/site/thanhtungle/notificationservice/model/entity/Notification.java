package site.thanhtungle.notificationservice.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import site.thanhtungle.commons.constant.enums.ENotificationType;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "notification")
public class Notification extends BaseEntity {

    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;

    @Column(name = "url")
    private String url;

    @Column(name = "type")
    private ENotificationType type;

    @OneToMany(
            mappedBy = "notification",
            fetch = FetchType.LAZY,
            cascade = {CascadeType.DETACH, CascadeType.MERGE,
            CascadeType.PERSIST, CascadeType.REFRESH}
    )
    private Set<NotificationRecipient> notificationRecipients;
}
