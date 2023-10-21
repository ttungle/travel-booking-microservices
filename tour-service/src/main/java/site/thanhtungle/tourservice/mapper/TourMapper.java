package site.thanhtungle.tourservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import site.thanhtungle.commons.model.dto.FileDto;
import site.thanhtungle.tourservice.model.dto.request.tour.TourRequestDTO;
import site.thanhtungle.tourservice.model.dto.response.tour.TourResponseDTO;
import site.thanhtungle.tourservice.model.dto.response.tourcategory.TourCategoryResponseDTO;
import site.thanhtungle.tourservice.model.entity.Tour;
import site.thanhtungle.tourservice.model.entity.TourCategory;
import site.thanhtungle.tourservice.model.entity.TourImage;

import java.util.Objects;

@Mapper(componentModel = "spring")
public interface TourMapper {

    Tour mapToTour(TourRequestDTO tourRequestDTO);

    @Mapping(target = "category", expression = "java(mapToCategoryResponseDto(tour.getCategory()))")
    TourResponseDTO mapToTourResponse(Tour tour);

    @Mapping(target = "tour", source = "tourEntity")
    @Mapping(target = "name", source = "fileDto.fileName")
    @Mapping(target = "url", source = "fileDto.url")
    TourImage mapFileDtoToTourImage(Tour tourEntity, FileDto fileDto);

    @Mapping(target = "tours", ignore = true)
    TourCategoryResponseDTO mapToCategoryResponseDto(TourCategory tourCategory);
}
