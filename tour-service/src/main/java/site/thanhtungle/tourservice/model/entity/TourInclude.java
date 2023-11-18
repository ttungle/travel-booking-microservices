package site.thanhtungle.tourservice.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
@Table(name = "tour_include")
public class TourInclude extends BaseEntity {

    @Column(name = "content")
    private String content;

    @JsonBackReference
    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {CascadeType.DETACH, CascadeType.MERGE,
                    CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(
            name = "tour_tour_include",
            joinColumns = @JoinColumn(name = "tour_include_id"),
            inverseJoinColumns = @JoinColumn(name = "tour_id")
    )
    private Set<Tour> tours;
}
