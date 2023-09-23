package site.thanhtungle.tourservice.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import site.thanhtungle.commons.model.response.success.BaseApiResponse;
import site.thanhtungle.commons.model.response.success.PagingApiResponse;
import site.thanhtungle.tourservice.model.dto.TourRequest;
import site.thanhtungle.tourservice.model.dto.TourResponse;
import site.thanhtungle.tourservice.service.TourService;

import java.util.List;

@RestController
@RequestMapping("${api.url.tour}")
@AllArgsConstructor
public class TourController {

    private final TourService tourService;
    @PostMapping
    public ResponseEntity<BaseApiResponse<TourResponse>> createTour(@RequestBody TourRequest tourRequest) {
        TourResponse tourResponse = tourService.saveTour(tourRequest);
        BaseApiResponse<TourResponse> response = new BaseApiResponse<>(HttpStatus.CREATED.value(), tourResponse);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseApiResponse<TourResponse>> getTour(@PathVariable("id") Long tourId) {
        TourResponse tourResponse = tourService.getTour(tourId);
        BaseApiResponse<TourResponse> response = new BaseApiResponse<>(HttpStatus.OK.value(), tourResponse);
        return ResponseEntity.ok().body(response);
    }

    /*
    * http://localhost:9191/api/v1/tours?page=1&size=10&sort=name:asc
    * */
    @GetMapping
    public ResponseEntity<PagingApiResponse<List<TourResponse>>> getAllTours(
            @RequestParam(name = "page", defaultValue = "1") Integer page,
            @RequestParam(name = "size", defaultValue = "10") Integer pageSize,
            @RequestParam(name = "sort", required = false) String sort
    ) {
        PagingApiResponse<List<TourResponse>> tourResponseList = tourService.getAllTours(page, pageSize, sort);
        return ResponseEntity.ok().body(tourResponseList);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BaseApiResponse<TourResponse>> updateTour(@PathVariable("id") Long tourId,
                                                                    @RequestBody TourRequest tourRequest) {
        TourResponse tourResponse = tourService.updateTour(tourId, tourRequest);
        BaseApiResponse<TourResponse> response = new BaseApiResponse<>(HttpStatus.OK.value(), tourResponse);
        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTour(@PathVariable("id") Long tourId) {
        tourService.deleteTour(tourId);
    }
}
