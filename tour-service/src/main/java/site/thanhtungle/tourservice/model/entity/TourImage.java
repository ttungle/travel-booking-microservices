package site.thanhtungle.tourservice.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tour_image")
public class TourImage extends BaseEntity implements Serializable {

    @Column(name = "name")
    private String name;

    @Column(name = "url")
    private String url;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY,
            cascade = {CascadeType.DETACH, CascadeType.MERGE,
                    CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "tour_id", referencedColumnName = "id")
    private Tour tour;
}
