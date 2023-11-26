package site.thanhtungle.tourservice.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import site.thanhtungle.commons.exception.CustomNotFoundException;
import site.thanhtungle.commons.model.response.success.PageInfo;
import site.thanhtungle.commons.model.response.success.PagingApiResponse;
import site.thanhtungle.tourservice.mapper.TourFAQMapper;
import site.thanhtungle.tourservice.model.dto.request.tourfqa.TourFAQRequestDTO;
import site.thanhtungle.tourservice.model.dto.response.tourfaq.TourFAQResponseDTO;
import site.thanhtungle.tourservice.model.entity.TourFAQ;
import site.thanhtungle.tourservice.repository.TourFAQRepository;
import site.thanhtungle.tourservice.service.TourFAQService;
import site.thanhtungle.tourservice.util.PageUtil;

import java.security.InvalidParameterException;
import java.util.List;

@Service
@AllArgsConstructor
public class TourFAQServiceImpl implements TourFAQService {

    private final TourFAQRepository tourFAQRepository;
    private final TourFAQMapper tourFAQMapper;

    @Override
    public TourFAQResponseDTO createTourFAQ(TourFAQRequestDTO tourFAQRequestDTO) {
        if (tourFAQRequestDTO == null) throw new InvalidParameterException("Tour FAQ body cannot be nul.");
        TourFAQ tourFAQ = tourFAQMapper.toEntityTourFAQ(tourFAQRequestDTO);
        TourFAQ savedTourFAQ = tourFAQRepository.save(tourFAQ);
        return tourFAQMapper.toTourFAQResponseDTO(savedTourFAQ);
    }

    @Override
    public TourFAQResponseDTO updateTourFAQ(Long tourFAQId, TourFAQRequestDTO tourFAQRequestDTO) {
        if (tourFAQId == null) throw new InvalidParameterException("Tour FAQ id cannot be null.");
        if (tourFAQRequestDTO == null) throw new InvalidParameterException("Tour FAQ body cannot be null.");

        TourFAQ tourFAQ = tourFAQRepository.findById(tourFAQId).orElseThrow(
                () -> new CustomNotFoundException("No tour FAQ found with that id."));
        tourFAQMapper.updateTourFAQ(tourFAQ, tourFAQRequestDTO);
        TourFAQ savedTourFAQ = tourFAQRepository.save(tourFAQ);
        return tourFAQMapper.toTourFAQResponseDTO(savedTourFAQ);
    }

    @Override
    public TourFAQResponseDTO getTourFAQ(Long tourFAQId) {
        if (tourFAQId == null) throw new InvalidParameterException("Tour FAQ id cannot be null.");
        TourFAQ tourFAQ = tourFAQRepository.findById(tourFAQId).orElseThrow(
                () -> new CustomNotFoundException("Not tour FAQ found with that id."));
        return tourFAQMapper.toTourFAQResponseDTO(tourFAQ);
    }

    @Override
    public PagingApiResponse<List<TourFAQResponseDTO>> getAllTourFAQs(Integer page, Integer pageSize, String sort) {
        PageRequest pageRequest = PageUtil.getPageRequest(page, pageSize, sort);

        Page<TourFAQ> tourFAQPaging = tourFAQRepository.findAll(pageRequest);
        List<TourFAQ> tourFAQList = tourFAQPaging.getContent();
        List<TourFAQResponseDTO> tourFAQResponseDTOData = tourFAQList.stream()
                .map(tourFAQMapper::toTourFAQResponseDTO)
                .toList();
        PageInfo pageInfo = new PageInfo(page, pageSize, tourFAQPaging.getTotalElements(), tourFAQPaging.getTotalPages());
        return new PagingApiResponse<>(HttpStatus.OK.value(), tourFAQResponseDTOData, pageInfo);
    }

    @Override
    public void deleteTourFAQ(Long tourFAQId) {
        TourFAQ tourFAQ = tourFAQRepository.findById(tourFAQId).orElseThrow(
                () -> new CustomNotFoundException("No tour FAQ found with that id."));
        tourFAQRepository.deleteById(tourFAQ.getId());
    }
}
