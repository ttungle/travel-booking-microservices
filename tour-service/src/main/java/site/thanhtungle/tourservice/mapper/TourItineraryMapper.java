package site.thanhtungle.tourservice.mapper;

import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;
import site.thanhtungle.commons.exception.CustomNotFoundException;
import site.thanhtungle.tourservice.model.dto.request.touritinerary.TourItineraryRequestDTO;
import site.thanhtungle.tourservice.model.dto.response.touritinerary.TourItineraryResponseDTO;
import site.thanhtungle.tourservice.model.entity.Tour;
import site.thanhtungle.tourservice.model.entity.TourItinerary;
import site.thanhtungle.tourservice.repository.TourRepository;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        injectionStrategy = InjectionStrategy.FIELD
)
public abstract class TourItineraryMapper {

    @Autowired
    private TourRepository tourRepository;

    public abstract TourItinerary toEntityTourItinerary(TourItineraryRequestDTO tourItineraryRequestDTO);

    public abstract TourItineraryResponseDTO toTourItineraryResponseDTO(TourItinerary tourItinerary);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "tour", source = "tourId", qualifiedByName = "toEntityTourFromId")
    public abstract void updateTourItinerary(
            TourItineraryRequestDTO tourItineraryRequestDTO, @MappingTarget TourItinerary tourItinerary);

    @Named("toEntityTourFromId")
    protected Tour toEntityTourFromId(Long tourId) {
        if (tourId == null) return null;
        return tourRepository.findById(tourId)
                .orElseThrow(() -> new CustomNotFoundException("No tour found with that id."));
    }
}
