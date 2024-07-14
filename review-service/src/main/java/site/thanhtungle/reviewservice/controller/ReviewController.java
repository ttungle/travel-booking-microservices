package site.thanhtungle.reviewservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import site.thanhtungle.commons.model.response.success.BaseApiResponse;
import site.thanhtungle.commons.model.response.success.PagingApiResponse;
import site.thanhtungle.reviewservice.model.criteria.ReviewCriteria;
import site.thanhtungle.reviewservice.model.dto.request.ReviewRequestDTO;
import site.thanhtungle.reviewservice.model.dto.request.ReviewUpdateRequestDTO;
import site.thanhtungle.reviewservice.model.entity.Review;
import site.thanhtungle.reviewservice.service.ReviewService;

import java.security.Principal;
import java.util.Map;

@RestController
@RequestMapping("${api.url.review}")
@AllArgsConstructor
@SecurityRequirement(name = "BearerAuth")
public class ReviewController {

    private final ReviewService reviewService;

    @Operation(summary = "Create new review")
    @PostMapping
    public ResponseEntity<BaseApiResponse<Review>> createReview(@Valid @RequestBody ReviewRequestDTO reviewRequestDTO) {
        Review review = reviewService.createReview(reviewRequestDTO);
        BaseApiResponse<Review> response = new BaseApiResponse<>(HttpStatus.CREATED.value(), review);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "Update review")
    @PutMapping("/{id}")
    public ResponseEntity<BaseApiResponse<Review>> updateReview(@PathVariable("id") Long reviewId,
                                                                @RequestBody ReviewUpdateRequestDTO reviewUpdateRequestDTO) {
        Review review = reviewService.updateReview(reviewId, reviewUpdateRequestDTO);
        BaseApiResponse<Review> response = new BaseApiResponse<>(HttpStatus.OK.value(), review);
        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "Toggle like of a review")
    @PutMapping("/{id}/like")
    public ResponseEntity<BaseApiResponse<String>> toggleReviewLike(Principal principal, @PathVariable("id") Long reviewId) {
        String message = reviewService.toggleLikeReview(principal.getName(), reviewId);
        BaseApiResponse<String> response = new BaseApiResponse<>(HttpStatus.OK.value(), message);
        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "Get all reviews", description = "Get all reviews with pagination, sort and filters. Support " +
            "filter fields: rating"
    )
    @GetMapping
    public ResponseEntity<PagingApiResponse<Map<String, Object>>> getAllReviews(@Valid ReviewCriteria reviewCriteria) {
        PagingApiResponse<Map<String, Object>> response = reviewService.getAllReview(reviewCriteria);
        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "Delete like of a review")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteReview(@PathVariable("id") Long reviewId) {
        reviewService.deleteReview(reviewId);
    }
}
