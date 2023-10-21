package site.thanhtungle.tourservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import site.thanhtungle.tourservice.model.dto.request.tourcategory.TourCategoryRequestDTO;
import site.thanhtungle.tourservice.model.dto.response.tour.BaseTourResponseDto;
import site.thanhtungle.tourservice.model.dto.response.tourcategory.TourCategoryResponseDTO;
import site.thanhtungle.tourservice.model.entity.Tour;
import site.thanhtungle.tourservice.model.entity.TourCategory;

import java.util.List;
import java.util.Objects;

@Mapper(componentModel = "spring")
public interface TourCategoryMapper {

    TourCategory mapCategoryDtoToCategory(TourCategoryRequestDTO tourCategoryRequestDTO);

    @Mapping(target = "tours", expression = "java(mapToBaseTourDto(tourCategory.getTours()))")
    TourCategoryResponseDTO mapCategoryToCategoryResponse(TourCategory tourCategory);

    List<BaseTourResponseDto> mapToBaseTourDto(List<Tour> tourList);
}
