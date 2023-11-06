package site.thanhtungle.tourservice.service;

import org.springframework.transaction.annotation.Transactional;
import site.thanhtungle.commons.model.response.success.PagingApiResponse;
import site.thanhtungle.tourservice.model.dto.request.tourcategory.TourCategoryRequestDTO;
import site.thanhtungle.tourservice.model.dto.response.tourcategory.TourCategoryResponseDTO;

import java.util.List;

@Transactional
public interface TourCategoryService {

    TourCategoryResponseDTO createTourCategory(TourCategoryRequestDTO tourCategoryRequestDTO);

    TourCategoryResponseDTO updateTourCategory(Long tourCategoryId, TourCategoryRequestDTO tourCategoryRequestDTO);

    @Transactional(readOnly = true)
    TourCategoryResponseDTO getTourCategory(Long categoryId);

    @Transactional(readOnly = true)
    PagingApiResponse<List<TourCategoryResponseDTO>> getAllTourCategories(Integer page, Integer pageSize, String sort);

    void deleteTourCategory(Long categoryId);
}
