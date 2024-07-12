package site.thanhtungle.reviewservice.model.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "review")
public class Review extends BaseEntity {

    @Column(name = "author")
    private String author;

    @Column(name = "avatar")
    private String avatar;

    @Column(name = "content")
    private String content;

    @Column(name = "language")
    private String language;

    @Column(name = "rating")
    private Integer rating;

    @Column(name = "like_count")
    private Integer likeCount;

    @JsonManagedReference
    @OneToMany(
            mappedBy = "review",
            fetch = FetchType.LAZY,
            cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}
    )
    private Set<ReviewLike> likes;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "tour_id")
    private Long tourId;
}
