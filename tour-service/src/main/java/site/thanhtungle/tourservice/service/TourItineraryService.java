package site.thanhtungle.tourservice.service;

import org.springframework.transaction.annotation.Transactional;
import site.thanhtungle.commons.model.response.success.PagingApiResponse;
import site.thanhtungle.tourservice.model.dto.request.touritinerary.TourItineraryRequestDTO;
import site.thanhtungle.tourservice.model.dto.response.touritinerary.TourItineraryResponseDTO;

import java.util.List;

@Transactional
public interface TourItineraryService {

    /**
     * Create tour itinerary
     * @param tourItineraryRequestDTO request body in json
     * @return {TourItineraryResponseDTO}
     * */
    TourItineraryResponseDTO createTourItinerary(TourItineraryRequestDTO tourItineraryRequestDTO);

    /**
     * Update tour itinerary
     * @param tourItineraryId tour itinerary id
     * @param tourItineraryRequestDTO request body in json
     * @return {TourItineraryResponseDTO}
     * */
    TourItineraryResponseDTO updateTourItinerary(Long tourItineraryId,TourItineraryRequestDTO tourItineraryRequestDTO);

    /**
     * Get tour itinerary by id
     * @param tourItineraryId tour itinerary id
     * @return {TourItineraryResponseDTO}
     * */
    @Transactional(readOnly = true)
    TourItineraryResponseDTO getTourItinerary(Long tourItineraryId);

    /**
     * Get all tour itinerary
     * @param page current page
     * @param pageSize number of item per page
     * @param sort sort param - example: name:asc
     * @return list of TourItineraryResponseDTO and page information
     * */
    @Transactional(readOnly = true)
    PagingApiResponse<List<TourItineraryResponseDTO>> getAllTourItinerary(Integer page, Integer pageSize, String sort);

    /**
     * Delete a tour itinerary
     * @param tourItineraryId tour id
     * */
    void deleteTourItinerary(Long tourItineraryId);
}
