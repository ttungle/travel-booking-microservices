package site.thanhtungle.tourservice.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import site.thanhtungle.commons.model.response.success.BaseApiResponse;
import site.thanhtungle.commons.model.response.success.PagingApiResponse;
import site.thanhtungle.tourservice.model.dto.request.tour.TourRequestDTO;
import site.thanhtungle.tourservice.model.dto.response.tour.TourResponseDTO;
import site.thanhtungle.tourservice.service.TourService;

import java.util.List;

@RestController
@RequestMapping("${api.url.tour}")
@AllArgsConstructor
public class TourController {

    private final TourService tourService;
    @PostMapping(value = "", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<BaseApiResponse<TourResponseDTO>> createTour(
            @RequestPart(value = "tour") TourRequestDTO tourRequestDTO,
            @RequestPart(value = "files", required = false) List<MultipartFile> fileList
    ) {
        TourResponseDTO tourResponseDTO = tourService.saveTour(tourRequestDTO, fileList);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        BaseApiResponse<TourResponseDTO> response = new BaseApiResponse<>(HttpStatus.CREATED.value(), tourResponseDTO);
        return ResponseEntity.status(HttpStatus.CREATED).headers(httpHeaders).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseApiResponse<TourResponseDTO>> getTour(@PathVariable("id") Long tourId) {
        TourResponseDTO tourResponseDTO = tourService.getTour(tourId);
        BaseApiResponse<TourResponseDTO> response = new BaseApiResponse<>(HttpStatus.OK.value(), tourResponseDTO);
        return ResponseEntity.ok().body(response);
    }

    /**
     *
     * @example http://localhost:9191/api/v1/tours?page=1&size=10&sort=name:asc
     * */
    @GetMapping
    public ResponseEntity<PagingApiResponse<List<TourResponseDTO>>> getAllTours(
            @RequestParam(name = "page", defaultValue = "1") Integer page,
            @RequestParam(name = "size", defaultValue = "25") Integer pageSize,
            @RequestParam(name = "sort", required = false) String sort
    ) {
        PagingApiResponse<List<TourResponseDTO>> tourResponseList = tourService.getAllTours(page, pageSize, sort);
        return ResponseEntity.ok().body(tourResponseList);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BaseApiResponse<TourResponseDTO>> updateTour(@PathVariable("id") Long tourId,
                                                                       @RequestBody TourRequestDTO tourRequestDTO) {
        TourResponseDTO tourResponseDTO = tourService.updateTour(tourId, tourRequestDTO);
        BaseApiResponse<TourResponseDTO> response = new BaseApiResponse<>(HttpStatus.OK.value(), tourResponseDTO);
        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTour(@PathVariable("id") Long tourId) {
        tourService.deleteTour(tourId);
    }
}
