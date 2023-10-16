package site.thanhtungle.tourservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import site.thanhtungle.commons.model.dto.FileDto;
import site.thanhtungle.tourservice.model.dto.TourRequest;
import site.thanhtungle.tourservice.model.dto.TourResponse;
import site.thanhtungle.tourservice.model.entity.Tour;
import site.thanhtungle.tourservice.model.entity.TourImage;

@Mapper(componentModel = "spring")
public interface TourMapper {

    Tour mapToTour(TourRequest tourRequest);

    TourResponse mapToTourResponse(Tour tour);

    @Mapping(target = "tour", source = "tourEntity")
    @Mapping(target = "name", source = "fileDto.fileName")
    @Mapping(target = "url", source = "fileDto.url")
    TourImage mapFileDtoToTourImage(Tour tourEntity, FileDto fileDto);
}
