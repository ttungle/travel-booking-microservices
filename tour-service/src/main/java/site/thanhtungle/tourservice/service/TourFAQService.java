package site.thanhtungle.tourservice.service;

import org.springframework.transaction.annotation.Transactional;
import site.thanhtungle.commons.model.response.success.PagingApiResponse;
import site.thanhtungle.tourservice.model.dto.request.tourfqa.TourFAQRequestDTO;
import site.thanhtungle.tourservice.model.dto.response.tourfaq.TourFAQResponseDTO;

import java.util.List;

@Transactional
public interface TourFAQService {

    TourFAQResponseDTO createTourFAQ(TourFAQRequestDTO tourFAQRequestDTO);

    TourFAQResponseDTO updateTourFAQ(Long tourFAQId, TourFAQRequestDTO tourFAQRequestDTO);

    @Transactional(readOnly = true)
    TourFAQResponseDTO getTourFAQ(Long tourFAQId);

    @Transactional(readOnly = true)
    PagingApiResponse<List<TourFAQResponseDTO>> getAllTourFAQs(Integer page, Integer pageSize, String sort);

    void deleteTourFAQ(Long tourFAQId);
}
