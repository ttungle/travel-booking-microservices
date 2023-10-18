package site.thanhtungle.tourservice.service;

import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import site.thanhtungle.tourservice.model.dto.request.tourcategory.TourCategoryRequestDTO;
import site.thanhtungle.tourservice.model.dto.response.tourcategory.TourCategoryResponseDTO;
import site.thanhtungle.tourservice.model.entity.TourCategory;

import java.util.List;

@Transactional
public interface TourCategoryService {

    TourCategoryResponseDTO createTourCategory(TourCategoryRequestDTO tourCategoryRequestDTO);

    TourCategoryResponseDTO updateTourCategory(Long tourCategoryId, TourCategoryRequestDTO tourCategoryRequestDTO);

    TourCategory getTourCategory(String categoryId);

    List<TourCategory> getAllTourCategories(Pageable pageable);

    void deleteTourCategory(String categoryId);
}
