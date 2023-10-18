package site.thanhtungle.tourservice.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import site.thanhtungle.tourservice.model.dto.request.tourcategory.TourCategoryRequestDTO;
import site.thanhtungle.tourservice.model.dto.response.tourcategory.TourCategoryResponseDTO;
import site.thanhtungle.tourservice.service.TourCategoryService;

@RestController
@RequestMapping("${api.url.tour}/category")
@AllArgsConstructor
public class TourCategoryController {

    private final TourCategoryService tourCategoryService;

    @PostMapping
    public ResponseEntity<TourCategoryResponseDTO> createTourCategory(
            @RequestBody TourCategoryRequestDTO tourCategoryRequestDTO) {
        TourCategoryResponseDTO response = tourCategoryService.createTourCategory(tourCategoryRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED.value()).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TourCategoryResponseDTO> updateTourCategory(
            @PathVariable("id") Long tourCategoryId,
            @RequestBody TourCategoryRequestDTO tourCategoryRequestDTO
    ) {
        TourCategoryResponseDTO response = tourCategoryService.updateTourCategory(tourCategoryId, tourCategoryRequestDTO);
        return ResponseEntity.ok().body(response);
    }
}
