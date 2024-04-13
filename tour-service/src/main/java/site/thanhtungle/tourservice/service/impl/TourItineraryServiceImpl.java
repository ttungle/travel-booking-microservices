package site.thanhtungle.tourservice.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import site.thanhtungle.commons.exception.CustomNotFoundException;
import site.thanhtungle.commons.model.response.success.PageInfo;
import site.thanhtungle.commons.model.response.success.PagingApiResponse;
import site.thanhtungle.tourservice.mapper.TourItineraryMapper;
import site.thanhtungle.tourservice.model.dto.request.touritinerary.TourItineraryRequestDTO;
import site.thanhtungle.tourservice.model.dto.response.touritinerary.TourItineraryResponseDTO;
import site.thanhtungle.tourservice.model.entity.Tour;
import site.thanhtungle.tourservice.model.entity.TourItinerary;
import site.thanhtungle.tourservice.repository.TourItineraryRepository;
import site.thanhtungle.tourservice.repository.TourRepository;
import site.thanhtungle.tourservice.service.TourItineraryService;
import site.thanhtungle.tourservice.util.PageUtil;

import java.security.InvalidParameterException;
import java.util.List;

import static site.thanhtungle.tourservice.constant.CacheConstants.TOUR_ITINERARY_CACHE;

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
    @CachePut(value = TOUR_ITINERARY_CACHE, key = "#tourItineraryId")
    public TourItineraryResponseDTO updateTourItinerary(Long tourItineraryId, TourItineraryRequestDTO tourItineraryRequestDTO) {
        if (tourItineraryRequestDTO == null) throw new InvalidParameterException("Tour itinerary body cannot be null");

        TourItinerary tourItinerary = tourItineraryRepository.findById(tourItineraryId).orElseThrow(
                () -> new CustomNotFoundException("No tour itinerary found with that id."));
        tourItineraryMapper.updateTourItinerary(tourItineraryRequestDTO, tourItinerary);

        TourItinerary savedTourItinerary = tourItineraryRepository.save(tourItinerary);
        return tourItineraryMapper.toTourItineraryResponseDTO(savedTourItinerary);
    }

    @Override
    @Cacheable(value = TOUR_ITINERARY_CACHE, key = "#tourItineraryId")
    public TourItineraryResponseDTO getTourItinerary(Long tourItineraryId) {
        if (tourItineraryId == null) throw new InvalidParameterException("Tour itinerary id should not be null.");

        TourItinerary tourItinerary = tourItineraryRepository.findById(tourItineraryId).orElseThrow(
                () -> new CustomNotFoundException("No tour itinerary found with that id."));
        return tourItineraryMapper.toTourItineraryResponseDTO(tourItinerary);
    }

    @Override
    @Cacheable(value = TOUR_ITINERARY_CACHE, key = "{#page, #pageSize, #sort}")
    public PagingApiResponse<List<TourItineraryResponseDTO>> getAllTourItinerary(Integer page, Integer pageSize, String sort) {
        PageRequest pageRequest = PageUtil.getPageRequest(page, pageSize, sort);

        Page<TourItinerary> tourItineraryListPaging = tourItineraryRepository.findAll(pageRequest);
        List<TourItinerary> tourItineraryList = tourItineraryListPaging.getContent();
        List<TourItineraryResponseDTO> tourItineraryResponseDTOList = tourItineraryList.stream()
                .map(tourItineraryMapper::toTourItineraryResponseDTO)
                .toList();
        PageInfo pageInfo = new PageInfo(page, pageSize, tourItineraryListPaging.getTotalElements());

        return new PagingApiResponse<>(HttpStatus.OK.value(), tourItineraryResponseDTOList, pageInfo);
    }

    @Override
    @CacheEvict(value = TOUR_ITINERARY_CACHE, key = "#tourItineraryId")
    public void deleteTourItinerary(Long tourItineraryId) {
        if (tourItineraryId == null) throw new InvalidParameterException("Tour itinerary id cannot be null.");
        tourItineraryRepository.findById(tourItineraryId).orElseThrow(
                () -> new CustomNotFoundException("No tour itinerary found with that id."));
        tourItineraryRepository.deleteById(tourItineraryId);
    }
}
