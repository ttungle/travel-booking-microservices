package site.thanhtungle.tourservice.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import site.thanhtungle.tourservice.model.dto.TourRequest;
import site.thanhtungle.tourservice.model.dto.TourResponse;
import site.thanhtungle.tourservice.model.response.BaseApiResponse;
import site.thanhtungle.tourservice.service.TourService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/tours")
@AllArgsConstructor
@Slf4j
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

    @GetMapping
    public ResponseEntity<BaseApiResponse<List<TourResponse>>> getAllTours() {
        List<TourResponse> tourResponseList = tourService.getAllTours();
        BaseApiResponse<List<TourResponse>> response = new BaseApiResponse<>();
        response.setStatus(HttpStatus.OK.value());
        response.setData(tourResponseList);

        return ResponseEntity.ok().body(response);
    }
}
