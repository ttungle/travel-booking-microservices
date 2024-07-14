package site.thanhtungle.tourservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import site.thanhtungle.commons.model.response.success.PagingApiResponse;
import site.thanhtungle.tourservice.model.dto.request.tourcategory.TourCategoryRequestDTO;
import site.thanhtungle.tourservice.model.dto.response.tourcategory.TourCategoryResponseDTO;
import site.thanhtungle.tourservice.service.TourCategoryService;

import java.util.List;

@RestController
@RequestMapping("${api.url.tourCategory}")
@AllArgsConstructor
public class TourCategoryController {

    private final TourCategoryService tourCategoryService;

    @Operation(summary = "Create tour category")
    @SecurityRequirement(name = "BearerAuth")
    @PostMapping
    @PreAuthorize("hasRole('admin')")
    public ResponseEntity<TourCategoryResponseDTO> createTourCategory(
            @RequestBody TourCategoryRequestDTO tourCategoryRequestDTO) {
        TourCategoryResponseDTO response = tourCategoryService.createTourCategory(tourCategoryRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED.value()).body(response);
    }

    @Operation(summary = "Update tour category")
    @SecurityRequirement(name = "BearerAuth")
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('admin')")
    public ResponseEntity<TourCategoryResponseDTO> updateTourCategory(
            @PathVariable("id") Long tourCategoryId,
            @RequestBody TourCategoryRequestDTO tourCategoryRequestDTO
    ) {
        TourCategoryResponseDTO response = tourCategoryService.updateTourCategory(tourCategoryId, tourCategoryRequestDTO);
        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "Get all tour categories", description = "Get all tour categories with pagination and sort.")
    @GetMapping
    public ResponseEntity<PagingApiResponse<List<TourCategoryResponseDTO>>> getAllTourCategories(
            @RequestParam(name = "page", defaultValue = "1") Integer page,
            @RequestParam(name = "pageSize", defaultValue = "25") Integer pageSize,
            @RequestParam(name = "sort", required = false) String sort
    ) {
        PagingApiResponse<List<TourCategoryResponseDTO>> response = tourCategoryService.getAllTourCategories(page, pageSize, sort);
        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "Get tour category by id")
    @GetMapping("/{id}")
    public ResponseEntity<TourCategoryResponseDTO> getTourCategory(@PathVariable("id") Long categoryId) {
        TourCategoryResponseDTO response = tourCategoryService.getTourCategory(categoryId);
        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "Delete tour category by id")
    @SecurityRequirement(name = "BearerAuth")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('admin')")
    public void deleteTourCategory(@PathVariable("id") Long categoryId) {
        tourCategoryService.deleteTourCategory(categoryId);
    }
}
