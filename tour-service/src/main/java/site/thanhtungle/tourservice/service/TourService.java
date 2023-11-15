package site.thanhtungle.tourservice.service;


import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import site.thanhtungle.commons.model.response.success.PagingApiResponse;
import site.thanhtungle.tourservice.model.dto.request.tour.TourRequestDTO;
import site.thanhtungle.tourservice.model.dto.response.tour.TourResponseDTO;

import java.util.List;

@Transactional
public interface TourService {

    /**
     * Create new tour
     * @param tourRequestDTO request body in json
     * @param fileList list of tour images
     * @param coverImage cover image
     * @param video tour video
     * @return {TourResponseDTO}
     * */
    TourResponseDTO saveTour(TourRequestDTO tourRequestDTO, List<MultipartFile> fileList,
                             MultipartFile coverImage, MultipartFile video);

    /**
     * Update a tour
     * @param tourId tour id
     * @param tourRequestDTO request body in json
     * @param fileList list of tour images
     * @param coverImage cover image
     * @param video tour video
     * @return {TourResponseDTO}
     * */
    TourResponseDTO updateTour(Long tourId, TourRequestDTO tourRequestDTO, List<MultipartFile> fileList,
                               MultipartFile coverImage, MultipartFile video);

    /**
     * Get tour by id
     * @param tourId tour id
     * @return {TourResponseDTO}
     * */
    @Transactional(readOnly = true)
    TourResponseDTO getTour(Long tourId);

    /**
     * Get all tours with pagination, sort
     * @param page current page
     * @param pageSize number of item per page
     * @param sort sort param - example: name:asc
     * @return {PagingApiResponse<List<TourResponseDTO>>} list of TourResponseDTO and page information
     * @example http://localhost:9191/api/v1/tours?page=1&pageSize=10&sort=name:asc
     * */
    @Transactional(readOnly = true)
    PagingApiResponse<List<TourResponseDTO>> getAllTours(Integer page, Integer pageSize, String sort);

    /**
     * Delete a tour
     * @param tourId tour id
     * */
    void deleteTour(Long tourId);
}
