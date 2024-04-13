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
import site.thanhtungle.tourservice.mapper.TourIncludeMapper;
import site.thanhtungle.tourservice.model.dto.request.tourinclude.TourIncludeRequestDTO;
import site.thanhtungle.tourservice.model.dto.response.tourinclude.TourIncludeResponseDTO;
import site.thanhtungle.tourservice.model.entity.TourInclude;
import site.thanhtungle.tourservice.repository.TourIncludeRepository;
import site.thanhtungle.tourservice.service.TourIncludeService;
import site.thanhtungle.tourservice.util.PageUtil;

import java.security.InvalidParameterException;
import java.util.List;

import static site.thanhtungle.tourservice.constant.CacheConstants.TOUR_INCLUDE_CACHE;

@Service
@AllArgsConstructor
public class TourIncludeServiceImpl implements TourIncludeService {

    private final TourIncludeRepository tourIncludeRepository;
    private final TourIncludeMapper tourIncludeMapper;

    @Override
    public TourIncludeResponseDTO createTourInclude(TourIncludeRequestDTO tourIncludeRequestDTO) {
        if (tourIncludeRequestDTO == null) throw new InvalidParameterException("Tour include body cannot be null.");
        TourInclude tourInclude = tourIncludeMapper.toEntityTourInclude(tourIncludeRequestDTO);
        TourInclude savedTourInclude = tourIncludeRepository.save(tourInclude);
        return tourIncludeMapper.toTourIncludeResponseDTO(savedTourInclude);
    }

    @Override
    @CachePut(value = TOUR_INCLUDE_CACHE, key = "#tourIncludeId")
    public TourIncludeResponseDTO updateTourInclude(Long tourIncludeId, TourIncludeRequestDTO tourIncludeRequestDTO) {
        if (tourIncludeId == null) throw new InvalidParameterException("Tour include id cannot be null.");
        if (tourIncludeRequestDTO == null) throw new InvalidParameterException("Tour include body cannot be null.");

        TourInclude tourInclude = tourIncludeRepository.findById(tourIncludeId).orElseThrow(() ->
                new CustomNotFoundException("Tour include cannot be found with that id."));
        tourIncludeMapper.updateTourInclude(tourInclude, tourIncludeRequestDTO);
        TourInclude savedTourInclude = tourIncludeRepository.save(tourInclude);
        return tourIncludeMapper.toTourIncludeResponseDTO(savedTourInclude);
    }

    @Override
    @Cacheable(value = TOUR_INCLUDE_CACHE, key = "{#page, #pageSize, #sort}")
    public PagingApiResponse<List<TourIncludeResponseDTO>> getAlTourInclude(Integer page, Integer pageSize, String sort) {
        PageRequest pageRequest = PageUtil.getPageRequest(page, pageSize, sort);

        Page<TourInclude> tourIncludesPaging = tourIncludeRepository.findAll(pageRequest);
        List<TourInclude> tourIncludeList = tourIncludesPaging.getContent();
        List<TourIncludeResponseDTO> tourIncludeResponseDTOList = tourIncludeList.stream()
                .map(tourIncludeMapper::toTourIncludeResponseDTO)
                .toList();
        PageInfo pageInfo =
                new PageInfo(page, pageSize, tourIncludesPaging.getTotalElements(), tourIncludesPaging.getTotalPages());

        return new PagingApiResponse<>(HttpStatus.OK.value(), tourIncludeResponseDTOList, pageInfo);
    }

    @Override
    @Cacheable(value = TOUR_INCLUDE_CACHE, key = "#tourIncludeId")
    public TourIncludeResponseDTO getTourInclude(Long tourIncludeId) {
        if (tourIncludeId == null) throw new InvalidParameterException("Tour include id cannot be null.");
        TourInclude tourInclude = tourIncludeRepository.findById(tourIncludeId).orElseThrow(() ->
                new CustomNotFoundException("No tour include found with that id."));
        return tourIncludeMapper.toTourIncludeResponseDTO(tourInclude);
    }

    @Override
    @CacheEvict(value = TOUR_INCLUDE_CACHE, key = "#tourIncludeId")
    public void deleteTourInclude(Long tourIncludeId) {
        TourInclude tourInclude = tourIncludeRepository.findById(tourIncludeId).orElseThrow(() ->
                new CustomNotFoundException("No tour include found with that id."));
        tourIncludeRepository.deleteById(tourInclude.getId());
    }
}
