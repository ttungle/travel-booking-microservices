package site.thanhtungle.bookingservice.service.rest;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import site.thanhtungle.bookingservice.model.dto.response.TourResponseDTO;
import site.thanhtungle.commons.model.response.success.BaseApiResponse;

@FeignClient(name = "TOUR-SERVICE", path = "/api/v1/tours")
public interface TourApiClient {

    @GetMapping("/{id}")
    ResponseEntity<BaseApiResponse<TourResponseDTO>> getTour(@PathVariable("id") Long tourId);
}
