package site.thanhtungle.tourservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import site.thanhtungle.commons.model.response.success.BaseApiResponse;
import site.thanhtungle.commons.model.response.success.PagingApiResponse;
import site.thanhtungle.tourservice.model.criteria.SearchTourCriteria;
import site.thanhtungle.tourservice.model.criteria.TourCriteria;
import site.thanhtungle.tourservice.model.dto.request.tour.TourRequestDTO;
import site.thanhtungle.tourservice.model.dto.request.tour.TourStatusRequestDTO;
import site.thanhtungle.tourservice.model.dto.response.tour.TourResponseDTO;
import site.thanhtungle.tourservice.service.TourService;

import java.util.List;

@RestController
@RequestMapping("${api.url.tour}")
@AllArgsConstructor
public class TourController {

    private final TourService tourService;

    @Operation(summary = "Create new tour")
    @SecurityRequirement(name = "BearerAuth")
    @PostMapping(value = "", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasRole('admin')")
    public ResponseEntity<BaseApiResponse<TourResponseDTO>> createTour(
            @RequestPart(value = "tour") TourRequestDTO tourRequestDTO,
            @RequestPart(value = "images", required = false) List<MultipartFile> tourImageList,
            @RequestPart(value = "coverImage", required = false) MultipartFile coverImage,
            @RequestPart(value = "video", required = false) MultipartFile video
    ) {
        TourResponseDTO tourResponseDTO = tourService.saveTour(tourRequestDTO, tourImageList, coverImage, video);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        BaseApiResponse<TourResponseDTO> response = new BaseApiResponse<>(HttpStatus.CREATED.value(), tourResponseDTO);
        return ResponseEntity.status(HttpStatus.CREATED).headers(httpHeaders).body(response);
    }

    @Operation(summary = "Update tour")
    @SecurityRequirement(name = "BearerAuth")
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('admin')")
    public ResponseEntity<BaseApiResponse<TourResponseDTO>> updateTour(
            @PathVariable("id") Long tourId,
            @RequestPart(value = "tour", required = false) TourRequestDTO tourRequestDTO,
            @RequestPart(value = "images", required = false) List<MultipartFile> tourImageList,
            @RequestPart(value = "coverImage", required = false) MultipartFile coverImage,
            @RequestPart(value = "video", required = false) MultipartFile video
    ) {
        TourResponseDTO tourResponseDTO = tourService.updateTour(tourId, tourRequestDTO,
                tourImageList, coverImage, video);
        BaseApiResponse<TourResponseDTO> response = new BaseApiResponse<>(HttpStatus.OK.value(), tourResponseDTO);
        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "Update tour status")
    @SecurityRequirement(name = "BearerAuth")
    @PutMapping("/{id}/status")
    @PreAuthorize("hasRole('admin')")
    public ResponseEntity<BaseApiResponse<TourResponseDTO>> updateTourStatus(
            @PathVariable("id") Long tourId, @RequestBody TourStatusRequestDTO tourStatusRequestDTO) {
        TourResponseDTO tourResponseDTO = tourService.updateTourStatus(tourId, tourStatusRequestDTO);
        BaseApiResponse<TourResponseDTO> response = new BaseApiResponse<>(HttpStatus.OK.value(), tourResponseDTO);
        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "Get tour by id")
    @GetMapping("/{id}")
    public ResponseEntity<BaseApiResponse<TourResponseDTO>> getTour(@PathVariable("id") Long tourId) {
        TourResponseDTO tourResponseDTO = tourService.getTour(tourId);
        BaseApiResponse<TourResponseDTO> response = new BaseApiResponse<>(HttpStatus.OK.value(), tourResponseDTO);
        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "Get all tours",
            description = "Get all tours with pagination, sort, filters. Supported filter fields: name, categoryId, " +
                        "price, priceDiscount, duration, ratingAverage, startDate, startLocation.")
    @GetMapping
    public ResponseEntity<PagingApiResponse<List<TourResponseDTO>>> getAllTours(@Valid TourCriteria tourCriteria) {
        PagingApiResponse<List<TourResponseDTO>> tourResponseList = tourService.getAllTours(tourCriteria);
        return ResponseEntity.ok().body(tourResponseList);
    }

    @Operation(summary = "Delete tour by id")
    @SecurityRequirement(name = "BearerAuth")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('admin')")
    public void deleteTour(@PathVariable("id") Long tourId) {
        tourService.deleteTour(tourId);
    }

    @Operation(summary = "Search all tours",
            description = "Get all tours with pagination, sort, filters. Search by tour name and start location.")
    @GetMapping("/search")
    public ResponseEntity<PagingApiResponse<List<TourResponseDTO>>> searchTour(@Valid SearchTourCriteria searchTourCriteria) {
        PagingApiResponse<List<TourResponseDTO>> tourList = tourService.searchTours(searchTourCriteria);
        return ResponseEntity.ok().body(tourList);
    }
}
