package site.thanhtungle.tourservice.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.Nullable;

import java.time.Instant;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tour")
public class Tour extends BaseEntity {

    @NotBlank(message = "Tour name cannot be empty or null.")
    @Column(name = "name")
    private String name;

    @Column(name = "duration")
    private String duration;

    @Column(name = "rating_average")
    private Float ratingAverage;

    @Column(name = "rating_quantity")
    private Integer ratingQuantity;

    @Column(name = "price")
    private Float price;

    @Column(name = "price_discount")
    private Float priceDiscount;

    @Column(name = "tour_type")
    private String tourType;

    @Column(name = "group_size")
    private Integer groupSize;

    @Column(name = "summary")
    private String summary;

    @Column(name = "description", columnDefinition = "text")
    private String description;

    @Column(name = "cover_image")
    private String coverImage;

    @Nullable
    @Column(name = "video")
    private String video;

    @Column(name = "start_location")
    private String startLocation;

    @Column(name = "start_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Instant startDate;

    @NotBlank(message = "Tour slug cannot be empty or null.")
    @Column(name = "slug")
    private String slug;

    @OneToMany(
            mappedBy = "tour",
            fetch = FetchType.LAZY,
            cascade = {CascadeType.ALL})
    private List<TourImage> images;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE,
                    CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "category_id")
    private TourCategory category;

    @OneToMany(
            mappedBy = "tour",
            fetch = FetchType.LAZY,
            cascade = {CascadeType.DETACH, CascadeType.MERGE,
            CascadeType.PERSIST, CascadeType.REFRESH})
    private List<TourItinerary> tourItineraries;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {CascadeType.DETACH, CascadeType.MERGE,
                    CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(
            name = "tour_tour_include",
            joinColumns = @JoinColumn(name = "tour_id"),
            inverseJoinColumns = @JoinColumn(name = "tour_include_id")
    )
    private List<TourInclude> tourIncludes;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {CascadeType.DETACH, CascadeType.MERGE,
                    CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(
            name = "tour_tour_exclude",
            joinColumns = @JoinColumn(name = "tour_id"),
            inverseJoinColumns = @JoinColumn(name = "tour_exclude_id")
    )
    private List<TourExclude> tourExcludes;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {CascadeType.DETACH, CascadeType.MERGE,
                    CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(
            name = "tour_tour_faq",
            joinColumns = @JoinColumn(name = "tour_id"),
            inverseJoinColumns = @JoinColumn(name = "tour_faq_id")

    )
    private List<TourFAQ> tourFAQs;

}
