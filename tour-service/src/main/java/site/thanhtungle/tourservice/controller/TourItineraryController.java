package site.thanhtungle.tourservice.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import site.thanhtungle.commons.model.response.success.BaseApiResponse;
import site.thanhtungle.commons.model.response.success.PagingApiResponse;
import site.thanhtungle.tourservice.model.dto.request.touritinerary.TourItineraryRequestDTO;
import site.thanhtungle.tourservice.model.dto.response.touritinerary.TourItineraryResponseDTO;
import site.thanhtungle.tourservice.service.TourItineraryService;

import java.util.List;

@RestController
@RequestMapping("${api.url.tourItinerary}")
@AllArgsConstructor
public class TourItineraryController {

    private final TourItineraryService tourItineraryService;

    @PostMapping
    public ResponseEntity<BaseApiResponse<TourItineraryResponseDTO>> createTourItinerary(
            @RequestBody TourItineraryRequestDTO tourItineraryRequestDTO) {
        TourItineraryResponseDTO responseDTO = tourItineraryService.createTourItinerary(tourItineraryRequestDTO);
        BaseApiResponse<TourItineraryResponseDTO> response = new BaseApiResponse<>(
                HttpStatus.CREATED.value(), responseDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BaseApiResponse<TourItineraryResponseDTO>> updateTourItinerary(
            @PathVariable("id") Long tourItineraryId,
            @RequestBody TourItineraryRequestDTO tourItineraryRequestDTO) {
        TourItineraryResponseDTO responseDTO = tourItineraryService.updateTourItinerary(
                tourItineraryId, tourItineraryRequestDTO);
        BaseApiResponse<TourItineraryResponseDTO> response = new BaseApiResponse<>(HttpStatus.OK.value(), responseDTO);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseApiResponse<TourItineraryResponseDTO>> getTourItinerary(
            @PathVariable("id") Long tourItineraryId) {
        TourItineraryResponseDTO responseDTO = tourItineraryService.getTourItinerary(tourItineraryId);
        BaseApiResponse<TourItineraryResponseDTO> response = new BaseApiResponse<>(HttpStatus.OK.value(), responseDTO);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping
    public ResponseEntity<PagingApiResponse<List<TourItineraryResponseDTO>>> getAllTourItinerary(
            @RequestParam(name = "page", defaultValue = "1") Integer page,
            @RequestParam(name = "pageSize", defaultValue = "25") Integer pageSize,
            @RequestParam(name = "sort", required = false) String sort
    ) {
        PagingApiResponse<List<TourItineraryResponseDTO>> responseDTOList = tourItineraryService.getAllTourItinerary(page, pageSize, sort);
        return ResponseEntity.ok().body(responseDTOList);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTourItinerary(@PathVariable("id") Long tourItineraryId) {
        tourItineraryService.deleteTourItinerary(tourItineraryId);
    }
}
