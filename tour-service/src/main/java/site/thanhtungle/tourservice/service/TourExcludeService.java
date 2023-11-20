package site.thanhtungle.tourservice.service;

import org.springframework.transaction.annotation.Transactional;
import site.thanhtungle.commons.model.response.success.PagingApiResponse;
import site.thanhtungle.tourservice.model.dto.request.tourexclude.TourExcludeRequestDTO;
import site.thanhtungle.tourservice.model.dto.response.tourexclude.TourExcludeResponseDTO;

import java.util.List;

@Transactional
public interface TourExcludeService {

    TourExcludeResponseDTO createTourExclude (TourExcludeRequestDTO tourExcludeRequestDTO);

    TourExcludeResponseDTO updateTourExclude (Long tourExcludeId, TourExcludeRequestDTO tourExcludeRequestDTO);

    @Transactional(readOnly = true)
    PagingApiResponse<List<TourExcludeResponseDTO>> getAllTourExclude(Integer page, Integer pageSize, String sort);

    @Transactional(readOnly = true)
    TourExcludeResponseDTO getTour(Long tourExcludeId);

    void deleteTourExclude(Long tourExcludeId);
}
