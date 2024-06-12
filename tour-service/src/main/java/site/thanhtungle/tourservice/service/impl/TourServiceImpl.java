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
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;
import site.thanhtungle.commons.constant.enums.EBookingItemStatus;
import site.thanhtungle.commons.constant.enums.ETourStatus;
import site.thanhtungle.commons.exception.CustomBadRequestException;
import site.thanhtungle.commons.exception.CustomNotFoundException;
import site.thanhtungle.commons.model.dto.FileDto;
import site.thanhtungle.commons.model.dto.PagingDTO;
import site.thanhtungle.commons.model.response.success.PageInfo;
import site.thanhtungle.commons.model.response.success.PagingApiResponse;
import site.thanhtungle.tourservice.mapper.TourMapper;
import site.thanhtungle.tourservice.model.criteria.SearchTourCriteria;
import site.thanhtungle.tourservice.model.criteria.TourCriteria;
import site.thanhtungle.tourservice.model.dto.request.booking.BookingItemStatusRequestDTO;
import site.thanhtungle.tourservice.model.dto.request.tour.TourRequestDTO;
import site.thanhtungle.tourservice.model.dto.request.tour.TourStatusRequestDTO;
import site.thanhtungle.tourservice.model.dto.response.tour.TourResponseDTO;
import site.thanhtungle.tourservice.model.entity.Tour;
import site.thanhtungle.tourservice.model.entity.TourImage;
import site.thanhtungle.tourservice.repository.TourRepository;
import site.thanhtungle.tourservice.service.TourService;
import site.thanhtungle.tourservice.service.rest.BookingApiClient;
import site.thanhtungle.tourservice.service.rest.StorageApiClient;
import site.thanhtungle.tourservice.service.specification.AndFilterSpecification;
import site.thanhtungle.tourservice.util.PageUtil;

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
    private final BookingApiClient bookingApiClient;

    @Override
    public TourResponseDTO saveTour(TourRequestDTO tourRequestDTO, List<MultipartFile> fileList,
                                    MultipartFile coverImage, MultipartFile video
    ) {
        Assert.notNull(tourRequestDTO, "The request body should not be empty.");

        Tour tour = tourMapper.toTour(tourRequestDTO);
        if (tour.getTourItineraries() != null && !tour.getTourItineraries().isEmpty()) {
            tour.getTourItineraries().forEach(itinerary -> itinerary.setTour(tour));
        }

        Tour firstSavedTour = tour;
        if (coverImage != null || fileList != null) firstSavedTour = tourRepository.save(tour);
        return getTourResponseDTO(fileList, coverImage, video, firstSavedTour);
    }

    @Override
    @CachePut(value = TOUR_CACHE, key = "#tourId")
    public TourResponseDTO updateTour(Long tourId, TourRequestDTO tourRequestDTO, List<MultipartFile> fileList,
                                      MultipartFile coverImage, MultipartFile video
    ) {
        Assert.notNull(tourId, "tourId cannot be null.");

        Tour tour = tourRepository.findById(tourId).orElseThrow(
                () -> new CustomNotFoundException("No tour found with that id."));
        tourMapper.updateTour(tourRequestDTO, tour);
        if (tour.getTourItineraries() != null && !tour.getTourItineraries().isEmpty()) {
            tour.getTourItineraries().forEach(itinerary -> itinerary.setTour(tour));
        }
        return getTourResponseDTO(fileList, coverImage, video, tour);
    }

    @Override
    @CachePut(value = TOUR_CACHE, key = "#tourId")
    public TourResponseDTO updateTourStatus(Long tourId, TourStatusRequestDTO tourRequestDTO) {
        Assert.notNull(tourId, "tourId cannot be null.");
        Assert.notNull(tourRequestDTO.getStatus(), "status cannot be null.");

        Tour tour = tourRepository.findById(tourId)
                .orElseThrow(() -> new CustomNotFoundException("No tour found with that id."));
        if (tour.getStatus() == ETourStatus.CANCELLED && tourRequestDTO.getStatus() == ETourStatus.ACTIVE) {
            throw new CustomBadRequestException("Cannot change status from CANCELLED to ACTIVE.");
        }
        // cancel all booking items related to this tour as this tour has been canceled.
        if (tour.getStatus() == ETourStatus.ACTIVE && tourRequestDTO.getStatus() == ETourStatus.CANCELLED) {
            BookingItemStatusRequestDTO body = new BookingItemStatusRequestDTO(EBookingItemStatus.CANCELLED);
            bookingApiClient.batchUpdateBookingItemStatus(tourId, body);
        }
        tour.setStatus(tourRequestDTO.getStatus());
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
        Assert.notNull(tourId, "tourId cannot be null.");
        Tour tour = tourRepository.findById(tourId).orElseThrow(
                () -> new CustomNotFoundException("No tour found with that id."));

        boolean isBookingItemExist = bookingApiClient.checkBookingItemExistByTourId(tourId);
        if (isBookingItemExist) {
            throw new CustomBadRequestException("Cannot delete the tour because this tour already has reservation.");
        }
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

    private TourResponseDTO getTourResponseDTO(List<MultipartFile> fileList, MultipartFile coverImage, MultipartFile video, Tour tour) {
        if (coverImage != null) uploadSingleFile(tour, coverImage, "coverImage_");
        if (video != null) uploadSingleFile(tour, video, "video_");
        if (fileList != null) uploadTourImage(tour, fileList);

        Tour updatedTour = tourRepository.save(tour);
        return tourMapper.toTourResponseDTO(updatedTour);
    }
}
