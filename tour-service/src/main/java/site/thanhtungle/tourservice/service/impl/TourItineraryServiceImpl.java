package site.thanhtungle.tourservice.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import site.thanhtungle.commons.exception.CustomNotFoundException;
import site.thanhtungle.tourservice.mapper.TourItineraryMapper;
import site.thanhtungle.tourservice.model.dto.request.touritinerary.TourItineraryRequestDTO;
import site.thanhtungle.tourservice.model.dto.response.touritinerary.TourItineraryResponseDTO;
import site.thanhtungle.tourservice.model.entity.Tour;
import site.thanhtungle.tourservice.model.entity.TourItinerary;
import site.thanhtungle.tourservice.repository.TourItineraryRepository;
import site.thanhtungle.tourservice.repository.TourRepository;
import site.thanhtungle.tourservice.service.TourItineraryService;

import java.security.InvalidParameterException;

@Service
@AllArgsConstructor
public class TourItineraryServiceImpl implements TourItineraryService {

    private final TourRepository tourRepository;
    private final TourItineraryRepository tourItineraryRepository;
    private final TourItineraryMapper tourItineraryMapper;

    @Override
    public TourItineraryResponseDTO createTourItinerary(TourItineraryRequestDTO tourItineraryRequestDTO) {
        if (tourItineraryRequestDTO == null) throw new InvalidParameterException("Tour itinerary body cannot be null");
        TourItinerary tourItinerary = tourItineraryMapper.toEntityTourItinerary(tourItineraryRequestDTO);

        Tour tour = tourRepository.findById(tourItineraryRequestDTO.getTourId())
                .orElseThrow(() -> new CustomNotFoundException("No tour found with given id"));
        tourItinerary.setTour(tour);

        TourItinerary savedTourItinerary = tourItineraryRepository.save(tourItinerary);
        return tourItineraryMapper.toTourItineraryResponseDTO(savedTourItinerary);
    }

    @Override
    public TourItineraryResponseDTO updateTourItinerary(Long tourItineraryId, TourItineraryRequestDTO tourItineraryRequestDTO) {
        if (tourItineraryRequestDTO == null) throw new InvalidParameterException("Tour itinerary body cannot be null");

        TourItinerary tourItinerary = tourItineraryRepository.findById(tourItineraryId).orElseThrow(
                () -> new CustomNotFoundException("No tour itinerary found with that id."));
        tourItineraryMapper.updateTourItinerary(tourItineraryRequestDTO, tourItinerary);

        TourItinerary savedTourItinerary = tourItineraryRepository.save(tourItinerary);
        return tourItineraryMapper.toTourItineraryResponseDTO(savedTourItinerary);
    }
}
