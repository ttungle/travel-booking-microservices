package site.thanhtungle.tourservice.service;


import org.springframework.transaction.annotation.Transactional;
import site.thanhtungle.commons.model.response.success.PagingApiResponse;
import site.thanhtungle.tourservice.model.dto.TourRequest;
import site.thanhtungle.tourservice.model.dto.TourResponse;

import java.util.List;

@Transactional
public interface TourService {

    public TourResponse saveTour(TourRequest tourRequest);

    @Transactional(readOnly = true)
    public TourResponse getTour(Long tourId);

    @Transactional(readOnly = true)
    public PagingApiResponse<List<TourResponse>> getAllTours(Integer page, Integer pageSize, String sort);

    public TourResponse updateTour(Long tourId, TourRequest tourRequest);

    void deleteTour(Long tourId);
}
