package site.thanhtungle.tourservice.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import site.thanhtungle.commons.exception.CustomNotFoundException;
import site.thanhtungle.commons.model.dto.FileDto;
import site.thanhtungle.commons.model.dto.PagingDTO;
import site.thanhtungle.commons.model.response.success.PageInfo;
import site.thanhtungle.commons.model.response.success.PagingApiResponse;
import site.thanhtungle.tourservice.mapper.TourMapper;
import site.thanhtungle.tourservice.model.criteria.SearchTourCriteria;
import site.thanhtungle.tourservice.model.criteria.TourCriteria;
import site.thanhtungle.tourservice.model.dto.request.tour.TourRequestDTO;
import site.thanhtungle.tourservice.model.dto.response.tour.TourResponseDTO;
import site.thanhtungle.tourservice.model.entity.Tour;
import site.thanhtungle.tourservice.model.entity.TourImage;
import site.thanhtungle.tourservice.repository.TourRepository;
import site.thanhtungle.tourservice.service.TourService;
import site.thanhtungle.tourservice.service.rest.StorageApiClient;
import site.thanhtungle.tourservice.service.specification.AndFilterSpecification;
import site.thanhtungle.tourservice.util.PageUtil;

import java.security.InvalidParameterException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static site.thanhtungle.tourservice.constant.CacheConstants.TOUR_CACHE;

@Service
@AllArgsConstructor
@Slf4j
public class TourServiceImpl implements TourService {

    private final TourRepository tourRepository;
    private final TourMapper tourMapper;
    private final StorageApiClient storageApiClient;
    private final AndFilterSpecification<Tour> andFilterSpecification;

    @Override
    public TourResponseDTO saveTour(TourRequestDTO tourRequestDTO, List<MultipartFile> fileList,
                                    MultipartFile coverImage, MultipartFile video
    ) {
        if (tourRequestDTO == null) throw new InvalidParameterException("The request body should not be empty.");

        Tour tour = tourMapper.toTour(tourRequestDTO);
        if (tour.getTourItineraries() != null && !tour.getTourItineraries().isEmpty()) {
            tour.getTourItineraries().forEach(itinerary -> itinerary.setTour(tour));
        }

        Tour firstSavedTour = tour;
        if (coverImage != null || fileList != null) firstSavedTour = tourRepository.save(tour);
        if (coverImage != null) uploadSingleFile(firstSavedTour, coverImage, "coverImage_");
        if (video != null) uploadSingleFile(firstSavedTour, video, "video_");
        if (fileList != null) uploadTourImage(firstSavedTour, fileList);

        Tour savedTour = tourRepository.save(firstSavedTour);
        return tourMapper.toTourResponseDTO(savedTour);
    }

    @Override
    @CachePut(value = TOUR_CACHE, key = "#tourId")
    public TourResponseDTO updateTour(Long tourId, TourRequestDTO tourRequestDTO, List<MultipartFile> fileList,
                                      MultipartFile coverImage, MultipartFile video
    ) {
        if (tourId == null) throw new InvalidParameterException("Tour id cannot be null.");

        Tour tour = tourRepository.findById(tourId).orElseThrow(
                () -> new CustomNotFoundException("No tour found with that id."));
        tourMapper.updateTour(tourRequestDTO, tour);
        if (tour.getTourItineraries() != null && !tour.getTourItineraries().isEmpty()) {
            tour.getTourItineraries().forEach(itinerary -> itinerary.setTour(tour));
        }
        if (coverImage != null) uploadSingleFile(tour, coverImage, "coverImage_");
        if (video != null) uploadSingleFile(tour, video, "video_");
        if (fileList != null) uploadTourImage(tour, fileList);

        Tour updatedTour = tourRepository.save(tour);
        return tourMapper.toTourResponseDTO(updatedTour);
    }

    @Override
    @Cacheable(value = TOUR_CACHE, key = "#tourId")
    public TourResponseDTO getTour(Long tourId) {
        Tour tour = tourRepository.findById(tourId).orElseThrow(
                () -> new CustomNotFoundException("No tour found with that id."));
        return tourMapper.toTourResponseDTO(tour);
    }

    @Override
    @Cacheable(value = TOUR_CACHE,
            key = "{#tourCriteria.page, #tourCriteria.pageSize, #tourCriteria.sort, #tourCriteria.filters}")
    public PagingApiResponse<List<TourResponseDTO>> getAllTours(TourCriteria tourCriteria) {
        PageRequest pageRequest = PageUtil.getPageRequest(
                tourCriteria.getPage(),
                tourCriteria.getPageSize(),
                tourCriteria.getSort()
        );

        List<String> allowedFields = Arrays.asList("name", "categoryId", "price", "priceDiscount", "duration",
                "ratingAverage", "startDate", "startLocation");
        Specification<Tour> filterBy = andFilterSpecification.getAndFilterSpecification(tourCriteria.getFilters(), allowedFields);

        Page<Tour> tourListPaging = tourRepository.findAll(filterBy, pageRequest);
        List<Tour> tourList = tourListPaging.getContent();
        List<TourResponseDTO> tourResponseDTOData = tourList.stream()
                .map(tourMapper::toTourResponseDTO)
                .toList();
        PageInfo pageInfo = new PageInfo(tourCriteria.getPage(), tourCriteria.getPageSize(),
                tourListPaging.getTotalElements(), tourListPaging.getTotalPages());

        return new PagingApiResponse<>(HttpStatus.OK.value(), tourResponseDTOData, pageInfo);
    }

    @Override
    @CacheEvict(value = TOUR_CACHE, key = "#tourId")
    public void deleteTour(Long tourId) {
        if (tourId == null) throw new InvalidParameterException("Tour id cannot be null.");
        Tour tour = tourRepository.findById(tourId).orElseThrow(
                () -> new CustomNotFoundException("No tour found with that id."));

        // delete tour files or images folder
        storageApiClient.deleteFolder("tours/" + tour.getId());
        tourRepository.deleteById(tour.getId());
    }

    @Override
    public PagingApiResponse<List<TourResponseDTO>> searchTours(SearchTourCriteria searchTourCriteria) {
        PagingDTO<Tour> tourPaging = tourRepository.searchBy(searchTourCriteria);
        List<TourResponseDTO> tourList = tourPaging.getData().stream().map(tourMapper::toTourResponseDTO).toList();
        return new PagingApiResponse<>(HttpStatus.OK.value(), tourList, tourPaging.getPagination());
    }

    private void uploadTourImage(Tour tour, List<MultipartFile> fileList) {
        try {
            List<String> filePathList = fileList.stream()
                    .map(file -> String.format("tours/%s/%s", tour.getId(), "tourImage_" + file.getOriginalFilename()))
                    .toList();
            List<FileDto> fileResponse = storageApiClient.uploadFiles(fileList, filePathList);

            List<TourImage> tourImageList = fileResponse.stream()
                    .map(fileDto -> tourMapper.toEntityTourImage(tour, fileDto)).collect(Collectors.toList());
            tour.setImages(tourImageList);
        } catch (Exception e) {
            log.info(String.valueOf(e));
        }
    }

    private void uploadSingleFile(Tour tour, MultipartFile file, String prefix) {
        String filePath = String.format("tours/%s/%s", tour.getId(), prefix + file.getOriginalFilename());
        FileDto fileResponse = storageApiClient.uploadFile(file, filePath);
        tour.setCoverImage(fileResponse.getUrl());
    }
}
