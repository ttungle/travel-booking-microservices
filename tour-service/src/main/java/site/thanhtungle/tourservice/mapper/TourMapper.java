package site.thanhtungle.tourservice.mapper;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;
import site.thanhtungle.commons.exception.CustomNotFoundException;
import site.thanhtungle.commons.model.dto.FileDto;
import site.thanhtungle.tourservice.model.dto.request.tour.TourRequestDTO;
import site.thanhtungle.tourservice.model.dto.response.tour.TourResponseDTO;
import site.thanhtungle.tourservice.model.dto.response.tourcategory.TourCategoryResponseDTO;
import site.thanhtungle.tourservice.model.entity.Tour;
import site.thanhtungle.tourservice.model.entity.TourCategory;
import site.thanhtungle.tourservice.model.entity.TourImage;
import site.thanhtungle.tourservice.repository.TourCategoryRepository;

@Mapper(componentModel = "spring")
public abstract class TourMapper {

    @Autowired
    private TourCategoryRepository tourCategoryRepository;

    public abstract Tour mapToTour(TourRequestDTO tourRequestDTO);

    @Mapping(target = "category", expression = "java(mapToCategoryResponseDto(tour.getCategory()))")
    public abstract TourResponseDTO mapToTourResponse(Tour tour);

    @Mapping(target = "tour", source = "tourEntity")
    @Mapping(target = "name", source = "fileDto.fileName")
    @Mapping(target = "url", source = "fileDto.url")
    public abstract TourImage mapFileDtoToTourImage(Tour tourEntity, FileDto fileDto);

    @Mapping(target = "tours", ignore = true)
    public abstract TourCategoryResponseDTO mapToCategoryResponseDto(TourCategory tourCategory);

    @AfterMapping
    protected void setTourCategoryFromId(TourRequestDTO tourRequestDTO, @MappingTarget Tour tour) {
        tour.setCategory(tourCategoryRepository.findById(tourRequestDTO.getCategoryId())
                .orElseThrow(() -> new CustomNotFoundException("No tour category found with given category id.")));
    }
}
