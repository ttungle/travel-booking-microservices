package site.thanhtungle.tourservice.mapper;

import org.mapstruct.Mapper;
import site.thanhtungle.tourservice.model.dto.TourRequest;
import site.thanhtungle.tourservice.model.dto.TourResponse;
import site.thanhtungle.tourservice.model.entity.Tour;

@Mapper(componentModel = "spring")
public interface TourMapper {

    public Tour mapToTour(TourRequest tourRequest);

    public TourResponse mapToTourResponse(Tour tour);
}
