package site.thanhtungle.tourservice.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import site.thanhtungle.commons.exception.CustomNotFoundException;
import site.thanhtungle.commons.model.dto.FileDto;
import site.thanhtungle.commons.model.response.success.PageInfo;
import site.thanhtungle.commons.model.response.success.PagingApiResponse;
import site.thanhtungle.tourservice.mapper.TourMapper;
import site.thanhtungle.tourservice.model.dto.request.tour.TourRequestDTO;
import site.thanhtungle.tourservice.model.dto.response.tour.TourResponseDTO;
import site.thanhtungle.tourservice.model.entity.Tour;
import site.thanhtungle.tourservice.model.entity.TourImage;
import site.thanhtungle.tourservice.repository.TourRepository;
import site.thanhtungle.tourservice.service.TourService;
import site.thanhtungle.tourservice.service.rest.StorageApiClient;
import site.thanhtungle.tourservice.util.PageUtil;

import java.security.InvalidParameterException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class TourServiceImpl implements TourService {

    private final TourRepository tourRepository;
    private final TourMapper tourMapper;
    private final StorageApiClient storageApiClient;

    @Override
    public TourResponseDTO saveTour(TourRequestDTO tourRequestDTO, List<MultipartFile> fileList) {
        if (tourRequestDTO == null) throw new InvalidParameterException("The request body should not be empty.");

        Tour tour = tourMapper.toTour(tourRequestDTO);
        if (!tour.getTourItineraries().isEmpty()) {
            tour.getTourItineraries().forEach(itinerary -> itinerary.setTour(tour));
        }
        if (fileList != null) uploadTourImage(tour, fileList);

        Tour savedTour =  tourRepository.save(tour);
        return tourMapper.toTourResponseDTO(savedTour);
    }

    @Override
    public TourResponseDTO getTour(Long tourId) {
        Tour tour = tourRepository.findById(tourId).orElseThrow(
                () -> new CustomNotFoundException("No tour found with that id."));
        return tourMapper.toTourResponseDTO(tour);
    }

    @Override
    public PagingApiResponse<List<TourResponseDTO>> getAllTours(Integer page, Integer pageSize, String sort) {
        PageRequest pageRequest = PageUtil.getPageRequest(page, pageSize, sort);

        Page<Tour> tourListPaging = tourRepository.findAll(pageRequest);
        List<Tour> tourList = tourListPaging.getContent();
        List<TourResponseDTO> tourResponseDTOData = tourList.stream()
                .map(tourMapper::toTourResponseDTO)
                .toList();
        PageInfo pageInfo = new PageInfo(page, pageSize,
                tourListPaging.getTotalElements(), tourListPaging.getTotalPages());

        return new PagingApiResponse<>(HttpStatus.OK.value(), tourResponseDTOData, pageInfo);
    }

    @Override
    @Transactional(noRollbackFor = Exception.class)
    public TourResponseDTO updateTour(Long tourId, TourRequestDTO tourRequestDTO, List<MultipartFile> fileList) {
        if (tourId == null) throw new InvalidParameterException("Tour id cannot be null.");

        Tour tour = tourRepository.findById(tourId).orElseThrow(
                () -> new CustomNotFoundException("No tour found with that id."));
        tourMapper.updateTour(tourRequestDTO, tour);
        if (!tour.getTourItineraries().isEmpty()) {
            tour.getTourItineraries().forEach(itinerary -> itinerary.setTour(tour));
        }
        if (fileList != null) uploadTourImage(tour, fileList);

        Tour updatedTour = tourRepository.save(tour);
        return tourMapper.toTourResponseDTO(updatedTour);
    }
    
    @Override
    public void deleteTour(Long tourId) {
        if(tourId == null) throw new InvalidParameterException("Tour id cannot be null.");
        Tour tour = tourRepository.findById(tourId).orElseThrow(
                () -> new CustomNotFoundException("No tour found with that id."));
        tourRepository.deleteById(tour.getId());
    }

    private void uploadTourImage(Tour tour, List<MultipartFile> fileList) {
       try {
           List<String> filePathList = fileList.stream()
                   .map(file -> String.format("tours/%s/%s", tour.getSlug(), file.getOriginalFilename()))
                   .toList();
           List<FileDto> fileResponse  = storageApiClient.uploadFiles(fileList, filePathList);

           List<TourImage> tourImageList = fileResponse.stream()
                   .map(fileDto -> tourMapper.toEntityTourImage(tour, fileDto)).collect(Collectors.toList());
           tour.setImages(tourImageList);
       } catch (Exception e) {
            log.info(String.valueOf(e));
       }
    }
}
