package site.thanhtungle.tourservice.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import site.thanhtungle.commons.exception.CustomNotFoundException;
import site.thanhtungle.tourservice.mapper.TourCategoryMapper;
import site.thanhtungle.tourservice.model.dto.request.tourcategory.TourCategoryRequestDTO;
import site.thanhtungle.tourservice.model.dto.response.tourcategory.TourCategoryResponseDTO;
import site.thanhtungle.tourservice.model.entity.Tour;
import site.thanhtungle.tourservice.model.entity.TourCategory;
import site.thanhtungle.tourservice.repository.TourCategoryRepository;
import site.thanhtungle.tourservice.repository.TourRepository;
import site.thanhtungle.tourservice.service.TourCategoryService;

import java.security.InvalidParameterException;
import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
public class TourCategoryServiceImpl implements TourCategoryService {

    private final TourRepository tourRepository;
    private final TourCategoryRepository tourCategoryRepository;
    private final TourCategoryMapper tourCategoryMapper;

    @Override
    public TourCategoryResponseDTO createTourCategory(TourCategoryRequestDTO tourCategoryRequestDTO) {
        if (Objects.isNull(tourCategoryRequestDTO))
            throw new InvalidParameterException("Tour category body cannot be null.");

        TourCategory tourCategoryBody = tourCategoryMapper.mapCategoryDtoToCategory(tourCategoryRequestDTO);
        if (Objects.nonNull(tourCategoryRequestDTO.getTourIdList()))
            findTourAndSetRelationship(tourCategoryRequestDTO, tourCategoryBody);

        TourCategory savedTourCategory = tourCategoryRepository.save(tourCategoryBody);
        return tourCategoryMapper.mapCategoryToCategoryResponse(savedTourCategory);
    }

    @Override
    public TourCategoryResponseDTO updateTourCategory(Long tourCategoryId, TourCategoryRequestDTO tourCategoryRequestDTO) {
        tourCategoryRepository.findById(tourCategoryId)
                .orElseThrow(() -> new CustomNotFoundException("No tour category found with that id."));

        TourCategory tourCategoryBody = tourCategoryMapper.mapCategoryDtoToCategory(tourCategoryRequestDTO);
        tourCategoryBody.setId(tourCategoryId);

        if (Objects.nonNull(tourCategoryRequestDTO.getTourIdList()))
            findTourAndSetRelationship(tourCategoryRequestDTO, tourCategoryBody);

        TourCategory updatedTourCategory = tourCategoryRepository.save(tourCategoryBody);
        return tourCategoryMapper.mapCategoryToCategoryResponse(updatedTourCategory);
    }

    @Override
    public TourCategory getTourCategory(String categoryId) {
        return null;
    }

    @Override
    public List<TourCategory> getAllTourCategories(Pageable pageable) {
        return null;
    }

    @Override
    public void deleteTourCategory(String categoryId) {

    }

    private void findTourAndSetRelationship(TourCategoryRequestDTO tourCategoryRequestDTO, TourCategory tourCategory) {
        List<Tour> foundedTourList = tourRepository.findTourByIdIn(tourCategoryRequestDTO.getTourIdList());
        if (Objects.nonNull(foundedTourList) || !foundedTourList.isEmpty()) {
            tourCategory.setTours(foundedTourList);
            foundedTourList.forEach(tour -> tour.setCategory(tourCategory));
        };
    }
}
