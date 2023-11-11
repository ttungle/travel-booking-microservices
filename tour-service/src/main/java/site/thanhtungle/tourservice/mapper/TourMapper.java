package site.thanhtungle.tourservice.mapper;

import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;
import site.thanhtungle.commons.exception.CustomNotFoundException;
import site.thanhtungle.commons.model.dto.FileDto;
import site.thanhtungle.tourservice.model.dto.request.tour.TourRequestDTO;
import site.thanhtungle.tourservice.model.dto.response.tour.TourResponseDTO;
import site.thanhtungle.tourservice.model.entity.Tour;
import site.thanhtungle.tourservice.model.entity.TourCategory;
import site.thanhtungle.tourservice.model.entity.TourImage;
import site.thanhtungle.tourservice.model.entity.TourItinerary;
import site.thanhtungle.tourservice.repository.TourCategoryRepository;
import site.thanhtungle.tourservice.repository.TourItineraryRepository;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(
        componentModel = "spring",
        uses = {TourCategoryMapper.class},
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        injectionStrategy = InjectionStrategy.FIELD
)
public abstract class TourMapper {

    @Autowired
    private TourCategoryRepository tourCategoryRepository;
    @Autowired
    private TourItineraryRepository tourItineraryRepository;

    @Mapping(target = "category", source = "categoryId", qualifiedByName = "toEntityTourCategoryFromId")
    @Mapping(target = "tourItineraries", source = "tourItineraryIds", qualifiedByName = "toEntityTourItineraryFromId")
    public abstract Tour toTour(TourRequestDTO tourRequestDTO);

    public abstract TourResponseDTO toTourResponseDTO(Tour tour);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "tour", source = "tour")
    @Mapping(target = "name", source = "fileDto.fileName")
    @Mapping(target = "url", source = "fileDto.url")
    public abstract TourImage toEntityTourImage(Tour tour, FileDto fileDto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "category", source = "categoryId", qualifiedByName = "toEntityTourCategoryFromId")
    @Mapping(target = "tourItineraries", source = "tourItineraryIds", qualifiedByName = "toEntityTourItineraryFromId")
    public abstract void updateTour(TourRequestDTO tourRequestDTO, @MappingTarget Tour tour);

    @Named("toEntityTourCategoryFromId")
    protected TourCategory toEntityTourCategoryFromId(Long categoryId) {
        if (categoryId == null) return null;
        return tourCategoryRepository.findById(categoryId)
                .orElseThrow(() -> new CustomNotFoundException("No tour category found with given category id."));
    }

    @Named("toEntityTourItineraryFromId")
    protected List<TourItinerary> toEntityTourItineraryFromId(List<Long> tourItineraryIdList) {
        if (tourItineraryIdList == null) return null;
        return tourItineraryIdList.stream()
                .map(id -> tourItineraryRepository
                        .findById(id)
                        .orElseThrow(() -> new CustomNotFoundException("No tour itinerary found with id: " + id))
                )
                .collect(Collectors.toList());
    }
}
