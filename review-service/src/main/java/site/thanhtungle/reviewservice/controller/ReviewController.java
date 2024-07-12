package site.thanhtungle.reviewservice.controller;

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
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping
    public ResponseEntity<BaseApiResponse<Review>> createReview(@Valid @RequestBody ReviewRequestDTO reviewRequestDTO) {
        Review review = reviewService.createReview(reviewRequestDTO);
        BaseApiResponse<Review> response = new BaseApiResponse<>(HttpStatus.CREATED.value(), review);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BaseApiResponse<Review>> updateReview(@PathVariable("id") Long reviewId,
                                                                @RequestBody ReviewUpdateRequestDTO reviewUpdateRequestDTO) {
        Review review = reviewService.updateReview(reviewId, reviewUpdateRequestDTO);
        BaseApiResponse<Review> response = new BaseApiResponse<>(HttpStatus.OK.value(), review);
        return ResponseEntity.ok().body(response);
    }

    @PutMapping("/{id}/like")
    public ResponseEntity<BaseApiResponse<String>> toggleReviewLike(Principal principal, @PathVariable("id") Long reviewId) {
        String message = reviewService.toggleLikeReview(principal.getName(), reviewId);
        BaseApiResponse<String> response = new BaseApiResponse<>(HttpStatus.OK.value(), message);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping
    public ResponseEntity<PagingApiResponse<Map<String, Object>>> getAllReviews(@Valid ReviewCriteria reviewCriteria) {
        PagingApiResponse<Map<String, Object>> response = reviewService.getAllReview(reviewCriteria);
        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteReview(@PathVariable("id") Long reviewId) {
        reviewService.deleteReview(reviewId);
    }
}
