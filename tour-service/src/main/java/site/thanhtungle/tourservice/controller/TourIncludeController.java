package site.thanhtungle.tourservice.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import site.thanhtungle.commons.model.response.success.BaseApiResponse;
import site.thanhtungle.commons.model.response.success.PagingApiResponse;
import site.thanhtungle.tourservice.model.dto.request.tourinclude.TourIncludeRequestDTO;
import site.thanhtungle.tourservice.model.dto.response.tourinclude.TourIncludeResponseDTO;
import site.thanhtungle.tourservice.service.TourIncludeService;

import java.util.List;

@RestController
@RequestMapping("${api.url.tourInclude}")
@AllArgsConstructor
public class TourIncludeController {

    private final TourIncludeService tourIncludeService;

    @PostMapping
    public ResponseEntity<BaseApiResponse<TourIncludeResponseDTO>> createTourInclude(
            @RequestBody TourIncludeRequestDTO tourIncludeRequestDTO) {
        TourIncludeResponseDTO tourIncludeResponse = tourIncludeService.createTourInclude(tourIncludeRequestDTO);
        BaseApiResponse<TourIncludeResponseDTO> response =
                new BaseApiResponse<>(HttpStatus.CREATED.value(), tourIncludeResponse);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BaseApiResponse<TourIncludeResponseDTO>> updateTourInclude(
            @PathVariable("id") Long tourIncludeId, @RequestBody TourIncludeRequestDTO tourIncludeRequestDTO) {
        TourIncludeResponseDTO tourIncludeResponse = tourIncludeService.updateTourInclude(tourIncludeId, tourIncludeRequestDTO);
        BaseApiResponse<TourIncludeResponseDTO> response =
                new BaseApiResponse<>(HttpStatus.OK.value(), tourIncludeResponse);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping
    public ResponseEntity<PagingApiResponse<List<TourIncludeResponseDTO>>> getAllTourInclude(
            @RequestParam(name = "page", defaultValue = "1") Integer page,
            @RequestParam(name = "pageSize", defaultValue = "25") Integer pageSize,
            @RequestParam(value = " sort", required = false) String sort
    ) {
        PagingApiResponse<List<TourIncludeResponseDTO>> response = tourIncludeService.getAlTourInclude(page, pageSize, sort);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseApiResponse<TourIncludeResponseDTO>> getTourInclude(@PathVariable("id") Long tourIncludeId) {
        TourIncludeResponseDTO tourIncludeResponse = tourIncludeService.getTourInclude(tourIncludeId);
        BaseApiResponse<TourIncludeResponseDTO> response = new BaseApiResponse<>(HttpStatus.OK.value(), tourIncludeResponse);
        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTourInclude(@PathVariable("id") Long tourIncludeId) {
        tourIncludeService.deleteTourInclude(tourIncludeId);
    }
}
