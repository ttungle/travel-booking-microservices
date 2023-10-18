package site.thanhtungle.tourservice.service;


import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import site.thanhtungle.commons.model.response.success.PagingApiResponse;
import site.thanhtungle.tourservice.model.dto.request.tour.TourRequestDTO;
import site.thanhtungle.tourservice.model.dto.response.tour.TourResponseDTO;

import java.util.List;

@Transactional
public interface TourService {

    public TourResponseDTO saveTour(TourRequestDTO tourRequestDTO, List<MultipartFile> fileList);

    @Transactional(readOnly = true)
    public TourResponseDTO getTour(Long tourId);

    @Transactional(readOnly = true)
    public PagingApiResponse<List<TourResponseDTO>> getAllTours(Integer page, Integer pageSize, String sort);

    public TourResponseDTO updateTour(Long tourId, TourRequestDTO tourRequestDTO);

    void deleteTour(Long tourId);
}
