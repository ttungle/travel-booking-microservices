package site.thanhtungle.tourservice.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @PostMapping
    public ResponseEntity<BaseApiResponse<TourExcludeResponseDTO>> createTourExclude(
            @RequestBody TourExcludeRequestDTO tourExcludeRequestDTO) {
        TourExcludeResponseDTO tourExcludeResponse = tourExcludeService.createTourExclude(tourExcludeRequestDTO);
        BaseApiResponse<TourExcludeResponseDTO> response =
                new BaseApiResponse<>(HttpStatus.CREATED.value(), tourExcludeResponse);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BaseApiResponse<TourExcludeResponseDTO>> updateTourExclude(
            @PathVariable("id") Long tourExcludeId, @RequestBody TourExcludeRequestDTO tourExcludeRequestDTO) {
        TourExcludeResponseDTO tourExcludeResponse = tourExcludeService.updateTourExclude(tourExcludeId, tourExcludeRequestDTO);
        BaseApiResponse<TourExcludeResponseDTO> response =
                new BaseApiResponse<>(HttpStatus.OK.value(), tourExcludeResponse);
        return ResponseEntity.ok().body(response);
    }

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

    @GetMapping("/{id}")
    public ResponseEntity<BaseApiResponse<TourExcludeResponseDTO>> getTourExclude(@PathVariable("id") Long tourExcludeId) {
        TourExcludeResponseDTO tourExcludeResponse = tourExcludeService.getTour(tourExcludeId);
        BaseApiResponse<TourExcludeResponseDTO> response =
                new BaseApiResponse<>(HttpStatus.OK.value(), tourExcludeResponse);
        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTourExclude(@PathVariable("id") Long tourExcludeId) {
        tourExcludeService.deleteTourExclude(tourExcludeId);
    }
}
