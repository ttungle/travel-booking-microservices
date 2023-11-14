package site.thanhtungle.tourservice.service;

import org.springframework.transaction.annotation.Transactional;
import site.thanhtungle.commons.model.response.success.PagingApiResponse;
import site.thanhtungle.tourservice.model.dto.request.tourcategory.TourCategoryRequestDTO;
import site.thanhtungle.tourservice.model.dto.response.tourcategory.TourCategoryResponseDTO;

import java.util.List;

@Transactional
public interface TourCategoryService {

    /**
     * Create tour category
     * @param tourCategoryRequestDTO request body in json
     * @return {TourCategoryResponseDTO}
     * */
    TourCategoryResponseDTO createTourCategory(TourCategoryRequestDTO tourCategoryRequestDTO);

    /**
     * Update tour category
     * @param tourCategoryId tour category id
     * @param tourCategoryRequestDTO request body in json
     * @return {TourCategoryResponseDTO}
     * */
    TourCategoryResponseDTO updateTourCategory(Long tourCategoryId, TourCategoryRequestDTO tourCategoryRequestDTO);

    /**
     * Get tour category by id
     * @param categoryId tour itinerary id
     * @return {TourCategoryResponseDTO}
     * */
    @Transactional(readOnly = true)
    TourCategoryResponseDTO getTourCategory(Long categoryId);

    /**
     * Get all tour category
     * @param page current page
     * @param pageSize number of item per page
     * @param sort sort param - example: name:asc
     * @return list of TourCategoryResponseDTO and page information
     * */
    @Transactional(readOnly = true)
    PagingApiResponse<List<TourCategoryResponseDTO>> getAllTourCategories(Integer page, Integer pageSize, String sort);

    /**
     * Delete a tour category
     * @param categoryId tour id
     * */
    void deleteTourCategory(Long categoryId);
}
