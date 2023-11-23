package site.thanhtungle.tourservice.mapper;

import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;
import site.thanhtungle.tourservice.model.dto.request.tourcategory.TourCategoryRequestDTO;
import site.thanhtungle.tourservice.model.dto.response.tourcategory.TourCategoryResponseDTO;
import site.thanhtungle.tourservice.model.entity.Tour;
import site.thanhtungle.tourservice.model.entity.TourCategory;
import site.thanhtungle.tourservice.repository.TourRepository;

import java.util.List;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        injectionStrategy = InjectionStrategy.FIELD
)
public abstract class TourCategoryMapper {

    @Autowired
    private TourRepository tourRepository;

    @Mapping(target = "tours", source = "tourIds", qualifiedByName = "toSimpleTourListFromId")
    public abstract TourCategory toTourCategory(TourCategoryRequestDTO tourCategoryRequestDTO);

    public abstract TourCategoryResponseDTO toCategoryResponseDTO(TourCategory tourCategory);

    @Named("toSimpleTourListFromId")
    protected List<Tour> toSimpleTourListFromId(List<Long> tourIdList) {
        if (tourIdList == null) return null;
        return tourRepository.findTourByIdIn(tourIdList);
    }
}
