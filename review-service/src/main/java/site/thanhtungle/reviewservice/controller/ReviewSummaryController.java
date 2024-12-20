package site.thanhtungle.reviewservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import site.thanhtungle.commons.model.response.success.BaseApiResponse;
import site.thanhtungle.reviewservice.model.dto.request.ReviewSummaryRequestDTO;
import site.thanhtungle.reviewservice.model.entity.ReviewSummary;
import site.thanhtungle.reviewservice.service.ReviewSummaryService;

@RestController
@RequestMapping("${api.url.reviewSummary}")
@AllArgsConstructor
@SecurityRequirement(name = "BearerAuth")
public class ReviewSummaryController {

    private final ReviewSummaryService reviewSummaryService;

    @Operation(summary = "Create new review summary")
    @PostMapping
    public ResponseEntity<BaseApiResponse<ReviewSummary>> createReviewSummary(
            @RequestBody ReviewSummaryRequestDTO reviewSummaryRequestDTO) {
        ReviewSummary reviewSummary = reviewSummaryService.createReviewSummary(reviewSummaryRequestDTO);
        BaseApiResponse<ReviewSummary> response = new BaseApiResponse<>(HttpStatus.CREATED.value(), reviewSummary);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "Calculate review summary by tourId")
    @GetMapping("/tours/{id}/calculate")
    public ResponseEntity<BaseApiResponse<ReviewSummary>> calculateReviewSummaryByTourId(@PathVariable("id") Long tourId) {
        ReviewSummary reviewSummary = reviewSummaryService.calculateReviewSummaryByTourId(tourId);
        BaseApiResponse<ReviewSummary> response = new BaseApiResponse<>(HttpStatus.OK.value(), reviewSummary);
        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "Get review summary by tourId")
    @GetMapping("/tours/{id}")
    public ResponseEntity<BaseApiResponse<ReviewSummary>> getReviewSummaryByTourId(@PathVariable("id") Long tourId) {
        ReviewSummary reviewSummary = reviewSummaryService.getReviewSummaryByTourId(tourId);
        BaseApiResponse<ReviewSummary> response = new BaseApiResponse<>(HttpStatus.OK.value(), reviewSummary);
        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "Delete review summary by id")
    @DeleteMapping("/{id}")
    public void deleteReviewSummary(@PathVariable("id") Long reviewSummaryId) {
        reviewSummaryService.deleteReviewSummary(reviewSummaryId);
    }
}
