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

    default List<BaseTourResponseDto> mapToBaseTourDto(List<Tour> tourList) {
        if (Objects.isNull(tourList)) return null;
        return tourList.stream().map(tour -> {
            BaseTourResponseDto baseTourResponseDto = new BaseTourResponseDto();
            baseTourResponseDto.setId(tour.getId());
            baseTourResponseDto.setName(tour.getName());
            baseTourResponseDto.setSummary(tour.getSummary());
            baseTourResponseDto.setDescription(tour.getDescription());
            baseTourResponseDto.setDuration(tour.getDuration());
            baseTourResponseDto.setSlug(tour.getSlug());
            baseTourResponseDto.setStartLocation(tour.getStartLocation());
            baseTourResponseDto.setPrice(tour.getPrice());
            baseTourResponseDto.setTourType(tour.getTourType());
            return baseTourResponseDto;
        }).toList();
    }
}
