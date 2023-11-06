package site.thanhtungle.tourservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.NullValuePropertyMappingStrategy;
import site.thanhtungle.tourservice.model.dto.request.tourcategory.TourCategoryRequestDTO;
import site.thanhtungle.tourservice.model.dto.response.tour.SimpleTourResponseDTO;
import site.thanhtungle.tourservice.model.dto.response.tourcategory.SimpleTourCategoryResponseDTO;
import site.thanhtungle.tourservice.model.dto.response.tourcategory.TourCategoryResponseDTO;
import site.thanhtungle.tourservice.model.entity.Tour;
import site.thanhtungle.tourservice.model.entity.TourCategory;

import java.util.List;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface TourCategoryMapper {

    TourCategory toTourCategory(TourCategoryRequestDTO tourCategoryRequestDTO);

    @Mapping(target = "tours", source = "tourCategory.tours", qualifiedByName = "toSimpleTourResponseDTO")
    TourCategoryResponseDTO toCategoryResponseDTO(TourCategory tourCategory);

    SimpleTourCategoryResponseDTO toBaseTourCategoryResponseDTO(TourCategory tourCategory);

    @Named("toSimpleTourResponseDTO")
    List<SimpleTourResponseDTO> toSimpleTourResponseDTO(List<Tour> tourList);
}
