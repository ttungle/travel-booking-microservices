package site.thanhtungle.tourservice.model.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.search.engine.backend.types.Sortable;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.*;
import org.springframework.lang.Nullable;
import site.thanhtungle.commons.constant.enums.ETourStatus;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tour")
@Indexed
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class Tour extends BaseEntity implements Serializable {

    @NotBlank(message = "Tour name cannot be empty or null.")
    @Column(name = "name")
    @FullTextField
    @KeywordField(name = "name_sort", sortable = Sortable.YES)
    private String name;

    @Column(name = "duration")
    private String duration;

    @Column(name = "rating_average")
    @GenericField(sortable = Sortable.YES)
    private Float ratingAverage;

    @Column(name = "rating_quantity")
    private Integer ratingQuantity;

    @Column(name = "price")
    @GenericField(sortable = Sortable.YES)
    private BigDecimal price;

    @Column(name = "price_discount")
    @GenericField(sortable = Sortable.YES)
    private BigDecimal priceDiscount;

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
    @FullTextField
    private String startLocation;

    @Column(name = "start_date")
    @Temporal(TemporalType.TIMESTAMP)
    @GenericField(sortable = Sortable.YES)
    private Instant startDate;

    @Column(name = "status")
    private ETourStatus status;

    @NotBlank(message = "Tour slug cannot be empty or null.")
    @Column(name = "slug")
    private String slug;

    @JsonManagedReference
    @OneToMany(
            mappedBy = "tour",
            fetch = FetchType.LAZY,
            cascade = {CascadeType.ALL})
    private List<TourImage> images;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE,
                    CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "category_id")
    @IndexedEmbedded
    private TourCategory category;

    @JsonManagedReference
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
