package site.thanhtungle.tourservice.service;

import jakarta.transaction.Transactional;
import site.thanhtungle.tourservice.model.dto.TourRequest;
import site.thanhtungle.tourservice.model.dto.TourResponse;
import site.thanhtungle.tourservice.model.response.PagingApiResponse;

import java.util.List;

@Transactional
public interface TourService {

    public TourResponse saveTour(TourRequest tourRequest);

    public PagingApiResponse<List<TourResponse>> getAllTours(Integer page, Integer pageSize, String sort);
}
