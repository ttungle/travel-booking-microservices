package site.thanhtungle.tourservice.service;

import org.springframework.transaction.annotation.Transactional;
import site.thanhtungle.commons.model.response.success.PagingApiResponse;
import site.thanhtungle.tourservice.model.dto.request.touritinerary.TourItineraryRequestDTO;
import site.thanhtungle.tourservice.model.dto.response.touritinerary.TourItineraryResponseDTO;

import java.util.List;

@Transactional
public interface TourItineraryService {

    TourItineraryResponseDTO createTourItinerary(TourItineraryRequestDTO tourItineraryRequestDTO);

    TourItineraryResponseDTO updateTourItinerary(Long tourItineraryId,TourItineraryRequestDTO tourItineraryRequestDTO);

    @Transactional(readOnly = true)
    TourItineraryResponseDTO getTourItinerary(Long tourItineraryId);

    @Transactional(readOnly = true)
    PagingApiResponse<List<TourItineraryResponseDTO>> getAllTourItinerary(Integer page, Integer pageSize, String sort);

    void deleteTourItinerary(Long tourItineraryId);
}
