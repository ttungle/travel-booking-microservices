package site.thanhtungle.tourservice.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import site.thanhtungle.commons.exception.CustomNotFoundException;
import site.thanhtungle.commons.model.response.success.PageInfo;
import site.thanhtungle.commons.model.response.success.PagingApiResponse;
import site.thanhtungle.tourservice.mapper.TourCategoryMapper;
import site.thanhtungle.tourservice.model.dto.request.tourcategory.TourCategoryRequestDTO;
import site.thanhtungle.tourservice.model.dto.response.tourcategory.TourCategoryResponseDTO;
import site.thanhtungle.tourservice.model.entity.Tour;
import site.thanhtungle.tourservice.model.entity.TourCategory;
import site.thanhtungle.tourservice.repository.TourCategoryRepository;
import site.thanhtungle.tourservice.repository.TourRepository;
import site.thanhtungle.tourservice.service.TourCategoryService;
import site.thanhtungle.tourservice.util.PageUtil;

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

        TourCategory tourCategoryBody = tourCategoryMapper.toTourCategory(tourCategoryRequestDTO);
        if (Objects.nonNull(tourCategoryRequestDTO.getTourIdList()))
            findTourAndSetRelationship(tourCategoryRequestDTO, tourCategoryBody);

        TourCategory savedTourCategory = tourCategoryRepository.save(tourCategoryBody);
        return tourCategoryMapper.toCategoryResponseDTO(savedTourCategory);
    }

    @Override
    public TourCategoryResponseDTO updateTourCategory(Long tourCategoryId, TourCategoryRequestDTO tourCategoryRequestDTO) {
        tourCategoryRepository.findById(tourCategoryId)
                .orElseThrow(() -> new CustomNotFoundException("No tour category found with that id."));

        TourCategory tourCategoryBody = tourCategoryMapper.toTourCategory(tourCategoryRequestDTO);
        tourCategoryBody.setId(tourCategoryId);

        if (Objects.nonNull(tourCategoryRequestDTO.getTourIdList()))
            findTourAndSetRelationship(tourCategoryRequestDTO, tourCategoryBody);

        TourCategory updatedTourCategory = tourCategoryRepository.save(tourCategoryBody);
        return tourCategoryMapper.toCategoryResponseDTO(updatedTourCategory);
    }

    @Override
    public TourCategoryResponseDTO getTourCategory(Long categoryId) {
        if (categoryId == null) throw new InvalidParameterException("category id cannot be null.");

        TourCategory tourCategory = tourCategoryRepository.findById(categoryId)
                .orElseThrow(() -> new CustomNotFoundException("No tour category found with that id."));
        return tourCategoryMapper.toCategoryResponseDTO(tourCategory);
    }

    @Override
    public PagingApiResponse<List<TourCategoryResponseDTO>> getAllTourCategories(Integer page, Integer pageSize, String sort) {
        PageRequest pageRequest = PageUtil.getPageRequest(page, pageSize, sort);

        Page<TourCategory> tourCategoryListPaging = tourCategoryRepository.findAll(pageRequest);
        List<TourCategory> tourCategoryList = tourCategoryListPaging.getContent();
        List<TourCategoryResponseDTO> tourCategoryResponseDTOData = tourCategoryList.stream()
                .map(tourCategoryMapper::toCategoryResponseDTO)
                .toList();
        PageInfo pageInfo = new PageInfo(page, pageSize,
                tourCategoryListPaging.getTotalElements(), tourCategoryListPaging.getTotalPages());

        return new PagingApiResponse<>(HttpStatus.OK.value(), tourCategoryResponseDTOData, pageInfo);
    }

    @Override
    public void deleteTourCategory(Long categoryId) {
        tourCategoryRepository.deleteById(categoryId);
    }

    private void findTourAndSetRelationship(TourCategoryRequestDTO tourCategoryRequestDTO, TourCategory tourCategory) {
        List<Tour> foundedTourList = tourRepository.findTourByIdIn(tourCategoryRequestDTO.getTourIdList());
        if (foundedTourList != null || !foundedTourList.isEmpty()) {
            tourCategory.setTours(foundedTourList);
            foundedTourList.forEach(tour -> tour.setCategory(tourCategory));
        };
    }
}
