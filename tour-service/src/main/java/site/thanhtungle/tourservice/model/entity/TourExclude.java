package site.thanhtungle.tourservice.model.entity;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tour_exclude")
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class TourExclude extends BaseEntity implements Serializable {

    @Column(name = "content")
    private String content;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {CascadeType.DETACH, CascadeType.MERGE,
                    CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(
            name = "tour_tour_exclude",
            joinColumns = @JoinColumn(name = "tour_exclude_id"),
            inverseJoinColumns = @JoinColumn(name = "tour_id")
    )
    private Set<Tour> tours;
}
