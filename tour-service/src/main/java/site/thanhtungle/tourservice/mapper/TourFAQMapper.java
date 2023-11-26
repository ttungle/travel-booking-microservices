package site.thanhtungle.tourservice.mapper;

import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;
import site.thanhtungle.tourservice.model.dto.request.tourfqa.TourFAQRequestDTO;
import site.thanhtungle.tourservice.model.dto.response.tourfaq.TourFAQResponseDTO;
import site.thanhtungle.tourservice.model.entity.Tour;
import site.thanhtungle.tourservice.model.entity.TourFAQ;
import site.thanhtungle.tourservice.repository.TourRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Mapper(
        componentModel = "spring",
        injectionStrategy = InjectionStrategy.FIELD,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public abstract class TourFAQMapper {

    @Autowired
    private TourRepository tourRepository;

    @Mapping(target = "tours", source = "tourIds", qualifiedByName = "toTourListFromId")
    public abstract TourFAQ toEntityTourFAQ(TourFAQRequestDTO tourFAQRequestDTO);

    public abstract TourFAQResponseDTO toTourFAQResponseDTO(TourFAQ tourFAQ);

    @Mapping(target = "tours", source = "tourIds", qualifiedByName = "toTourListFromId")
    public abstract void updateTourFAQ(@MappingTarget TourFAQ tourFAQ, TourFAQRequestDTO tourFAQRequestDTO);

    @Named("toTourListFromId")
    protected Set<Tour> toTourListFromId(List<Long> tourIdList) {
        if (tourIdList == null || tourIdList.isEmpty()) return null;
        List<Tour> tourList = tourRepository.findTourByIdIn(tourIdList);
        return new HashSet<>(tourList);
    }
}
