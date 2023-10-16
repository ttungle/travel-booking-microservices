package site.thanhtungle.tourservice.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import site.thanhtungle.commons.exception.CustomNotFoundException;
import site.thanhtungle.commons.model.dto.FileDto;
import site.thanhtungle.commons.model.response.success.PageInfo;
import site.thanhtungle.commons.model.response.success.PagingApiResponse;
import site.thanhtungle.tourservice.mapper.TourMapper;
import site.thanhtungle.tourservice.model.dto.TourRequest;
import site.thanhtungle.tourservice.model.dto.TourResponse;
import site.thanhtungle.tourservice.model.entity.Tour;
import site.thanhtungle.tourservice.model.entity.TourImage;
import site.thanhtungle.tourservice.repository.TourRepository;
import site.thanhtungle.tourservice.service.TourService;
import site.thanhtungle.tourservice.service.rest.StorageApiClient;

import java.security.InvalidParameterException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
@Slf4j
public class TourServiceImpl implements TourService {

    private final TourRepository tourRepository;
    private final TourMapper tourMapper;
    private final StorageApiClient storageApiClient;

    @Override
    public TourResponse saveTour(TourRequest tourRequest, List<MultipartFile> fileList) {
        Tour tour = tourMapper.mapToTour(tourRequest);

        if (Objects.nonNull(fileList)) {
            List<String> filePathList = fileList.stream()
                    .map(file -> String.format("tours/%s/%s", tourRequest.getSlug(), file.getOriginalFilename()))
                    .toList();
            List<FileDto> fileResponse  = storageApiClient.uploadFiles(fileList, filePathList);
            List<TourImage> tourImageList = fileResponse.stream()
                            .map(fileDto -> tourMapper.mapFileDtoToTourImage(tour, fileDto))
                            .toList();
            tour.setImages(tourImageList);
        }

        Tour savedTour =  tourRepository.save(tour);
        return tourMapper.mapToTourResponse(savedTour);
    }

    @Override
    public TourResponse getTour(Long tourId) {
        Tour tour = tourRepository.findById(tourId).orElseThrow(
                () -> new CustomNotFoundException("No tour found with that id."));
        return tourMapper.mapToTourResponse(tour);
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

    @Override
    public TourResponse updateTour(Long tourId, TourRequest tourRequest) {
        if(Objects.isNull(tourId)) throw new InvalidParameterException("Tour id cannot be null.");

        Tour tour = tourRepository.findById(tourId).orElseThrow(
                () -> new CustomNotFoundException("No tour found with that id."));
        Tour tourBody = tourMapper.mapToTour(tourRequest);
        tourBody.setId(tour.getId());
        Tour updatedTour = tourRepository.save(tourBody);

        return tourMapper.mapToTourResponse(updatedTour);
    }

    @Override
    public void deleteTour(Long tourId) {
        if(Objects.isNull(tourId)) throw new InvalidParameterException("Tour id cannot be null.");
        Tour tour = tourRepository.findById(tourId).orElseThrow(
                () -> new CustomNotFoundException("No tour found with that id."));
        tourRepository.deleteById(tour.getId());
    }

    private Sort getSort(String sort) {
        int sortParameterCount = StringUtils.countOccurrencesOf(sort, ",");
        if (sortParameterCount >= 2) throw new InvalidParameterException("Cannot sort more than 2 fields.");

        List<Sort.Order> orderList;
        if (!sort.contains(":")) {
            orderList = Arrays.stream(sort.split(","))
                    .map(field -> new Sort.Order(Sort.Direction.ASC, field))
                    .toList();
            return Sort.by(orderList);
        }

        String[] sortElements = sort.split(",");
        orderList = Arrays.stream(sortElements).map(element -> {
            if (!element.contains(":")) return new Sort.Order(Sort.Direction.ASC, element);

            String[] splitFields = element.split(":");
            return switch (splitFields[1]) {
                case "asc" -> new Sort.Order(Sort.Direction.ASC, splitFields[0]);
                case "desc" -> new Sort.Order(Sort.Direction.DESC, splitFields[0]);
                default -> throw new InvalidParameterException(
                        "Invalid sort direction. Sorting direction should be 'asc' or 'desc'.");
            };
        }).toList();
        return orderList.isEmpty() ? null : Sort.by(orderList);
    }
}
