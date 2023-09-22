package site.thanhtungle.tourservice.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import site.thanhtungle.commons.model.response.BaseApiResponse;
import site.thanhtungle.commons.model.response.PagingApiResponse;
import site.thanhtungle.tourservice.model.dto.TourRequest;
import site.thanhtungle.tourservice.model.dto.TourResponse;
import site.thanhtungle.tourservice.service.TourService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/tours")
@AllArgsConstructor
public class TourController {

    private final TourService tourService;
    @PostMapping
    public ResponseEntity<BaseApiResponse<TourResponse>> createTour(@RequestBody TourRequest tourRequest) {
        TourResponse tourResponse = tourService.saveTour(tourRequest);
        BaseApiResponse<TourResponse> response = new BaseApiResponse<>();
        response.setStatus(HttpStatus.CREATED.value());
        response.setData(tourResponse);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
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
}
