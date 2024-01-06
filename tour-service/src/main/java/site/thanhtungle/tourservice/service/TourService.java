package site.thanhtungle.tourservice.service;


import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import site.thanhtungle.commons.model.response.success.PagingApiResponse;
import site.thanhtungle.tourservice.model.criteria.SearchTourCriteria;
import site.thanhtungle.tourservice.model.criteria.TourCriteria;
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
     *
     * @param tourCriteria contains page, pageSize, sort, filter fields,...
     *  page: current page
     *  pageSize: number of item per page
     *  sort: sort param - example: name:asc
     *  price: range of price to filter - example: price[between]=0,10000
     * @return {PagingApiResponse<List<TourResponseDTO>>} list of TourResponseDTO and page information
     * @example /api/v1/tours?page=1&pageSize=10&sort=name:asc&filters[categoryId][eq]=1&filters[price][between]=0,10000
     * */
    @Transactional(readOnly = true)
    PagingApiResponse<List<TourResponseDTO>> getAllTours(TourCriteria tourCriteria);

    /**
     * Delete a tour
     * @param tourId tour id
     * */
    void deleteTour(Long tourId);

    PagingApiResponse<List<TourResponseDTO>> searchTours(SearchTourCriteria searchTourCriteria);
}
