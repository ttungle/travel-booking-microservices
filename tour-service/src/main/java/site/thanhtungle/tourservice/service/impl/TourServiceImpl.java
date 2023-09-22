package site.thanhtungle.tourservice.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import site.thanhtungle.commons.model.response.PageInfo;
import site.thanhtungle.commons.model.response.PagingApiResponse;
import site.thanhtungle.tourservice.mapper.TourMapper;
import site.thanhtungle.tourservice.model.dto.TourRequest;
import site.thanhtungle.tourservice.model.dto.TourResponse;
import site.thanhtungle.tourservice.model.entity.Tour;
import site.thanhtungle.tourservice.repository.TourRepository;
import site.thanhtungle.tourservice.service.TourService;

import java.security.InvalidParameterException;
import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
@Slf4j
public class TourServiceImpl implements TourService {

    private final TourRepository tourRepository;
    private final TourMapper tourMapper;

    @Override
    public TourResponse saveTour(TourRequest tourRequest) {
        Tour tour = tourMapper.mapToTour(tourRequest);
        Tour savedTour =  tourRepository.save(tour);
        return tourMapper.mapToTourResponse(savedTour);
    }

    @Override
    public PagingApiResponse<List<TourResponse>> getAllTours(Integer page, Integer pageSize, String sort) {
        if (Objects.isNull(page) || page < 1) throw new InvalidParameterException("Invalid page.");
        if (Objects.isNull(pageSize) || pageSize < 0) throw new InvalidParameterException("Invalid page size.");

        PageRequest pageRequest;
        if (Objects.nonNull(sort) || !sort.isBlank()) {
            Sort sortRequest = getSort(sort);
            if (Objects.isNull(sortRequest)) throw new InvalidParameterException("Invalid sort value.");
            pageRequest = PageRequest.of(page - 1, pageSize, sortRequest);
        } else {
            pageRequest = PageRequest.of(page - 1, pageSize);
        }

        Page<Tour> tourListPaging = tourRepository.findAll(pageRequest);
        List<Tour> tourList = tourListPaging.getContent();
        List<TourResponse> tourResponseData = tourList.stream()
                .map(tourMapper::mapToTourResponse)
                .toList();
        PageInfo pageInfo = new PageInfo(page, pageSize,
                tourListPaging.getTotalElements(), tourListPaging.getTotalPages());

        return new PagingApiResponse<>(HttpStatus.OK.value(), tourResponseData, pageInfo);
    }

    private Sort getSort(String sort) {
        if (!sort.contains(":")) return Sort.by(Sort.Direction.ASC, sort);

        String[] sortElements = sort.split(":");
        return switch (sortElements[1]) {
            case "asc" -> Sort.by(Sort.Direction.ASC, sortElements[0]);
            case "desc" -> Sort.by(Sort.Direction.DESC, sortElements[0]);
            default -> null;
        };
    }
}
