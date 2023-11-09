package site.thanhtungle.tourservice.service;

import org.springframework.transaction.annotation.Transactional;
import site.thanhtungle.tourservice.model.dto.request.touritinerary.TourItineraryRequestDTO;
import site.thanhtungle.tourservice.model.dto.response.touritinerary.TourItineraryResponseDTO;

@Transactional
public interface TourItineraryService {

    TourItineraryResponseDTO createTourItinerary(TourItineraryRequestDTO tourItineraryRequestDTO);

    TourItineraryResponseDTO updateTourItinerary(Long tourItineraryId,TourItineraryRequestDTO tourItineraryRequestDTO);
}
