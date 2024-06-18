package site.thanhtungle.reviewservice.service.rest;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "TOUR-SERVICE", path = "/api/v1/tours")
public interface TourApiClient {
}
