package site.thanhtungle.tourservice.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import site.thanhtungle.commons.exception.CustomNotFoundException;
import site.thanhtungle.commons.model.response.success.PageInfo;
import site.thanhtungle.commons.model.response.success.PagingApiResponse;
import site.thanhtungle.tourservice.mapper.TourExcludeMapper;
import site.thanhtungle.tourservice.model.dto.request.tourexclude.TourExcludeRequestDTO;
import site.thanhtungle.tourservice.model.dto.response.tourexclude.TourExcludeResponseDTO;
import site.thanhtungle.tourservice.model.entity.TourExclude;
import site.thanhtungle.tourservice.repository.TourExcludeRepository;
import site.thanhtungle.tourservice.service.TourExcludeService;
import site.thanhtungle.tourservice.util.PageUtil;

import java.security.InvalidParameterException;
import java.util.List;

import static site.thanhtungle.tourservice.constant.CacheConstants.TOUR_EXCLUDE_CACHE;

@Service
@AllArgsConstructor
public class TourExcludeServiceImpl implements TourExcludeService {

    private final TourExcludeRepository tourExcludeRepository;
    private final TourExcludeMapper tourExcludeMapper;

    @Override
    public TourExcludeResponseDTO createTourExclude(TourExcludeRequestDTO tourExcludeRequestDTO) {
        if (tourExcludeRequestDTO == null) throw new InvalidParameterException("Tour exclude body cannot be null.");
        TourExclude tourExclude = tourExcludeMapper.toEntityTourExclude(tourExcludeRequestDTO);
        TourExclude savedTourExclude = tourExcludeRepository.save(tourExclude);
        return tourExcludeMapper.toTourExcludeResponseDTO(savedTourExclude);
    }

    @Override
    @CachePut(value = TOUR_EXCLUDE_CACHE, key = "#tourExcludeId")
    public TourExcludeResponseDTO updateTourExclude(Long tourExcludeId, TourExcludeRequestDTO tourExcludeRequestDTO) {
        if (tourExcludeId == null) throw new InvalidParameterException("Tour exclude id cannot be null");
        if (tourExcludeRequestDTO == null) throw new InvalidParameterException("Tour exclude body cannot be null.");

        TourExclude tourExclude = tourExcludeRepository.findById(tourExcludeId).orElseThrow(
                () -> new CustomNotFoundException("No tour found with that id"));
        tourExcludeMapper.updateTourExclude(tourExclude, tourExcludeRequestDTO);
        TourExclude savedTourExclude = tourExcludeRepository.save(tourExclude);
        return tourExcludeMapper.toTourExcludeResponseDTO(savedTourExclude);
    }

    @Override
    @Cacheable(value = TOUR_EXCLUDE_CACHE, key = "{#page, #pageSize, #sort}")
    public PagingApiResponse<List<TourExcludeResponseDTO>> getAllTourExclude(Integer page, Integer pageSize, String sort) {
        PageRequest pageRequest = PageUtil.getPageRequest(page, pageSize, sort);

        Page<TourExclude> tourExcludePaging = tourExcludeRepository.findAll(pageRequest);
        List<TourExclude> tourExcludeList = tourExcludePaging.getContent();
        List<TourExcludeResponseDTO> tourExcludeResponseDTOList = tourExcludeList.stream()
                .map(tourExcludeMapper::toTourExcludeResponseDTO)
                .toList();
        PageInfo pageInfo =
                new PageInfo(page, pageSize, tourExcludePaging.getTotalElements(), tourExcludePaging.getTotalPages());
        return new PagingApiResponse<>(HttpStatus.OK.value(), tourExcludeResponseDTOList, pageInfo);
    }

    @Override
    @Cacheable(value = TOUR_EXCLUDE_CACHE, key = "#tourExcludeId")
    public TourExcludeResponseDTO getTour(Long tourExcludeId) {
        if (tourExcludeId == null) throw new InvalidParameterException("Tour exclude id cannot be null.");
        TourExclude tourExclude = tourExcludeRepository.findById(tourExcludeId).orElseThrow(
                () -> new CustomNotFoundException("No tour exclude found with that id."));
        return tourExcludeMapper.toTourExcludeResponseDTO(tourExclude);
    }

    @Override
    public void deleteTourExclude(Long tourExcludeId) {
        if (tourExcludeId == null) throw new InvalidParameterException("Tour exclude id cannot be null.");
        tourExcludeRepository.deleteById(tourExcludeId);
    }
}
