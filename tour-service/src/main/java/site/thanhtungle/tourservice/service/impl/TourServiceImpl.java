package site.thanhtungle.tourservice.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
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

@Service
@AllArgsConstructor
@Slf4j
public class TourServiceImpl implements TourService {

    private final TourRepository tourRepository;
    private final TourMapper tourMapper;
    private final StorageApiClient storageApiClient;

    @Override
    public TourResponseDTO saveTour(TourRequestDTO tourRequestDTO, List<MultipartFile> fileList) {
        Tour tour = tourMapper.mapToTour(tourRequestDTO);

        if (fileList != null) {
            List<String> filePathList = fileList.stream()
                    .map(file -> String.format("tours/%s/%s", tourRequestDTO.getSlug(), file.getOriginalFilename()))
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
    public TourResponseDTO getTour(Long tourId) {
        Tour tour = tourRepository.findById(tourId).orElseThrow(
                () -> new CustomNotFoundException("No tour found with that id."));
        return tourMapper.mapToTourResponse(tour);
    }

    @Override
    public PagingApiResponse<List<TourResponseDTO>> getAllTours(Integer page, Integer pageSize, String sort) {
        PageRequest pageRequest = PageUtil.getPageRequest(page, pageSize, sort);

        Page<Tour> tourListPaging = tourRepository.findAll(pageRequest);
        List<Tour> tourList = tourListPaging.getContent();
        List<TourResponseDTO> tourResponseDTOData = tourList.stream()
                .map(tourMapper::mapToTourResponse)
                .toList();
        PageInfo pageInfo = new PageInfo(page, pageSize,
                tourListPaging.getTotalElements(), tourListPaging.getTotalPages());

        return new PagingApiResponse<>(HttpStatus.OK.value(), tourResponseDTOData, pageInfo);
    }

    @Override
    public TourResponseDTO updateTour(Long tourId, TourRequestDTO tourRequestDTO) {
        if(tourId == null) throw new InvalidParameterException("Tour id cannot be null.");

        Tour tour = tourRepository.findById(tourId).orElseThrow(
                () -> new CustomNotFoundException("No tour found with that id."));
        Tour tourBody = tourMapper.mapToTour(tourRequestDTO);
        tourBody.setId(tour.getId());
        Tour updatedTour = tourRepository.save(tourBody);

        return tourMapper.mapToTourResponse(updatedTour);
    }

    @Override
    public void deleteTour(Long tourId) {
        if(tourId == null) throw new InvalidParameterException("Tour id cannot be null.");
        Tour tour = tourRepository.findById(tourId).orElseThrow(
                () -> new CustomNotFoundException("No tour found with that id."));
        tourRepository.deleteById(tour.getId());
    }
}
