package site.thanhtungle.tourservice.service;

import org.springframework.transaction.annotation.Transactional;
import site.thanhtungle.commons.model.response.success.PagingApiResponse;
import site.thanhtungle.tourservice.model.dto.request.tourinclude.TourIncludeRequestDTO;
import site.thanhtungle.tourservice.model.dto.response.tourinclude.TourIncludeResponseDTO;

import java.util.List;

@Transactional
public interface TourIncludeService {

    TourIncludeResponseDTO createTourInclude(TourIncludeRequestDTO tourIncludeRequestDTO);

    TourIncludeResponseDTO updateTourInclude(Long tourIncludeId, TourIncludeRequestDTO tourIncludeRequestDTO);

    @Transactional(readOnly = true)
    PagingApiResponse<List<TourIncludeResponseDTO>> getAlTourInclude(Integer page, Integer pageSize, String sort);

    @Transactional(readOnly = true)
    TourIncludeResponseDTO getTourInclude(Long tourIncludeId);

    void deleteTourInclude(Long tourIncludeId);
}
