package site.thanhtungle.tourservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import site.thanhtungle.commons.model.response.success.BaseApiResponse;
import site.thanhtungle.commons.model.response.success.PagingApiResponse;
import site.thanhtungle.tourservice.model.dto.request.tourexclude.TourExcludeRequestDTO;
import site.thanhtungle.tourservice.model.dto.response.tourexclude.TourExcludeResponseDTO;
import site.thanhtungle.tourservice.service.TourExcludeService;

import java.util.List;

@RestController
@RequestMapping("${api.url.tourExclude}")
@AllArgsConstructor
public class TourExcludeController {

    private final TourExcludeService tourExcludeService;

    @Operation(summary = "Create new tour exclude")
    @SecurityRequirement(name = "BearerAuth")
    @PostMapping
    @PreAuthorize("hasRole('admin')")
    public ResponseEntity<BaseApiResponse<TourExcludeResponseDTO>> createTourExclude(
            @RequestBody TourExcludeRequestDTO tourExcludeRequestDTO) {
        TourExcludeResponseDTO tourExcludeResponse = tourExcludeService.createTourExclude(tourExcludeRequestDTO);
        BaseApiResponse<TourExcludeResponseDTO> response =
                new BaseApiResponse<>(HttpStatus.CREATED.value(), tourExcludeResponse);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "Update tour exclude")
    @SecurityRequirement(name = "BearerAuth")
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('admin')")
    public ResponseEntity<BaseApiResponse<TourExcludeResponseDTO>> updateTourExclude(
            @PathVariable("id") Long tourExcludeId, @RequestBody TourExcludeRequestDTO tourExcludeRequestDTO) {
        TourExcludeResponseDTO tourExcludeResponse = tourExcludeService.updateTourExclude(tourExcludeId, tourExcludeRequestDTO);
        BaseApiResponse<TourExcludeResponseDTO> response =
                new BaseApiResponse<>(HttpStatus.OK.value(), tourExcludeResponse);
        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "Get all tour excludes", description = "Get all tour excludes with pagination and sort.")
    @GetMapping
    public ResponseEntity<PagingApiResponse<List<TourExcludeResponseDTO>>> getAllTourExclude(
            @RequestParam(name = "page", defaultValue = "1") Integer page,
            @RequestParam(name = "pageSize", defaultValue = "25") Integer pageSize,
            @RequestParam(name = "sort", required = false) String sort
            ) {
        PagingApiResponse<List<TourExcludeResponseDTO>> tourExcludeResponse =
                tourExcludeService.getAllTourExclude(page, pageSize, sort);
        return ResponseEntity.ok().body(tourExcludeResponse);
    }

    @Operation(summary = "Get tour exclude by id")
    @GetMapping("/{id}")
    public ResponseEntity<BaseApiResponse<TourExcludeResponseDTO>> getTourExclude(@PathVariable("id") Long tourExcludeId) {
        TourExcludeResponseDTO tourExcludeResponse = tourExcludeService.getTour(tourExcludeId);
        BaseApiResponse<TourExcludeResponseDTO> response =
                new BaseApiResponse<>(HttpStatus.OK.value(), tourExcludeResponse);
        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "Delete tour exclude by id")
    @SecurityRequirement(name = "BearerAuth")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('admin')")
    public void deleteTourExclude(@PathVariable("id") Long tourExcludeId) {
        tourExcludeService.deleteTourExclude(tourExcludeId);
    }
}
