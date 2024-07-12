package site.thanhtungle.reviewservice.service;

import org.springframework.transaction.annotation.Transactional;
import site.thanhtungle.commons.model.response.success.PagingApiResponse;
import site.thanhtungle.reviewservice.model.criteria.ReviewCriteria;
import site.thanhtungle.reviewservice.model.dto.request.ReviewRequestDTO;
import site.thanhtungle.reviewservice.model.dto.request.ReviewUpdateRequestDTO;
import site.thanhtungle.reviewservice.model.entity.Review;

import java.util.Map;

@Transactional
public interface ReviewService {

    Review createReview(ReviewRequestDTO reviewRequestDTO);

    Review updateReview(Long reviewId, ReviewUpdateRequestDTO reviewUpdateRequestDTO);

    String toggleLikeReview(String userId, Long reviewId);

    @Transactional(readOnly = true)
    PagingApiResponse<Map<String, Object>> getAllReview(ReviewCriteria reviewCriteria);

    void deleteReview(Long reviewId);
}
