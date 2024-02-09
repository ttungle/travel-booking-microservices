package site.thanhtungle.tourservice.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import site.thanhtungle.commons.model.response.success.BaseApiResponse;
import site.thanhtungle.commons.model.response.success.PagingApiResponse;
import site.thanhtungle.tourservice.model.dto.request.tourfqa.TourFAQRequestDTO;
import site.thanhtungle.tourservice.model.dto.response.tourfaq.TourFAQResponseDTO;
import site.thanhtungle.tourservice.service.TourFAQService;

import java.util.List;

@RestController
@RequestMapping("${api.url.tourFAQ}")
@AllArgsConstructor
public class TourFAQController {

    private final TourFAQService tourFAQService;

    @PostMapping
    @PreAuthorize("hasRole('admin')")
    public ResponseEntity<BaseApiResponse<TourFAQResponseDTO>> createTourFAQ(@RequestBody TourFAQRequestDTO tourFAQRequestDTO) {
        TourFAQResponseDTO tourFAQResponse = tourFAQService.createTourFAQ(tourFAQRequestDTO);
        BaseApiResponse<TourFAQResponseDTO> response = new BaseApiResponse<>(HttpStatus.CREATED.value(), tourFAQResponse);
        return ResponseEntity.ok().body(response);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('admin')")
    public ResponseEntity<BaseApiResponse<TourFAQResponseDTO>> updateTourFAQ(@PathVariable("id") Long tourFAQId,
                                            @RequestBody TourFAQRequestDTO tourFAQRequestDTO) {
        TourFAQResponseDTO tourFAQResponse = tourFAQService.updateTourFAQ(tourFAQId, tourFAQRequestDTO);
        BaseApiResponse<TourFAQResponseDTO> response = new BaseApiResponse<>(HttpStatus.OK.value(), tourFAQResponse);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseApiResponse<TourFAQResponseDTO>> getTourFAQ(@PathVariable("id") Long tourFAQId) {
        TourFAQResponseDTO tourFAQResponse = tourFAQService.getTourFAQ(tourFAQId);
        BaseApiResponse<TourFAQResponseDTO> response = new BaseApiResponse<>(HttpStatus.OK.value(), tourFAQResponse);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping
    public ResponseEntity<PagingApiResponse<List<TourFAQResponseDTO>>> getAllTourFAQs(
            @RequestParam(name = "page", defaultValue = "1") Integer page,
            @RequestParam(name = "pageSize", defaultValue = "25") Integer pageSize,
            @RequestParam(name = "sort", required = false) String sort
            ) {
        PagingApiResponse<List<TourFAQResponseDTO>> tourFAQResponse = tourFAQService.getAllTourFAQs(page, pageSize, sort);
        return ResponseEntity.ok().body(tourFAQResponse);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('admin')")
    public void deleteTourFAQ(@PathVariable("id") Long tourFAQId) {
        tourFAQService.deleteTourFAQ(tourFAQId);
    }
}
