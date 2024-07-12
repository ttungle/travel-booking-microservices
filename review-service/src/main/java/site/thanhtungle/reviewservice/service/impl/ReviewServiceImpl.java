package site.thanhtungle.reviewservice.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import site.thanhtungle.commons.constant.enums.ETourStatus;
import site.thanhtungle.commons.exception.CustomBadRequestException;
import site.thanhtungle.commons.exception.CustomNotFoundException;
import site.thanhtungle.commons.model.response.success.BaseApiResponse;
import site.thanhtungle.commons.model.response.success.PageInfo;
import site.thanhtungle.commons.model.response.success.PagingApiResponse;
import site.thanhtungle.commons.util.CommonPageUtil;
import site.thanhtungle.reviewservice.mapper.ReviewMapper;
import site.thanhtungle.reviewservice.model.criteria.ReviewCriteria;
import site.thanhtungle.reviewservice.model.dto.request.ReviewRequestDTO;
import site.thanhtungle.reviewservice.model.dto.request.ReviewUpdateRequestDTO;
import site.thanhtungle.reviewservice.model.dto.response.TourResponseDTO;
import site.thanhtungle.reviewservice.model.entity.Review;
import site.thanhtungle.reviewservice.model.entity.ReviewLike;
import site.thanhtungle.reviewservice.model.entity.ReviewSummary;
import site.thanhtungle.reviewservice.repository.ReviewLikeRepository;
import site.thanhtungle.reviewservice.repository.ReviewRepository;
import site.thanhtungle.reviewservice.repository.ReviewSummaryRepository;
import site.thanhtungle.reviewservice.service.ReviewService;
import site.thanhtungle.reviewservice.service.ReviewSummaryService;
import site.thanhtungle.reviewservice.service.rest.TourApiClient;
import site.thanhtungle.reviewservice.service.specification.AndFilterSpecification;

import java.util.*;

@Service
@AllArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final ReviewSummaryRepository reviewSummaryRepository;
    private final ReviewLikeRepository reviewLikeRepository;
    private final ReviewSummaryService reviewSummaryService;
    private final ReviewMapper reviewMapper;
    private final TourApiClient tourApiClient;
    private final AndFilterSpecification<Review> andFilterSpecification;

    @Override
    public Review createReview(ReviewRequestDTO reviewRequestDTO) {
        ResponseEntity<BaseApiResponse<TourResponseDTO>> response = tourApiClient.getTour(reviewRequestDTO.getTourId());
        if (Objects.isNull(response) || Objects.isNull(response.getBody().getData().getId())) {
            throw new CustomNotFoundException("No tour found with tourId : " + reviewRequestDTO.getTourId());
        }
        if (response.getBody().getData().getStatus() != ETourStatus.ACTIVE) {
            throw new CustomBadRequestException("Cannot create new review because tour is inactive.");
        }
        // if no review summary found with tourId, then create a new one
        Optional<ReviewSummary> reviewSummary = reviewSummaryRepository.findByTourId(reviewRequestDTO.getTourId());
        if (Objects.isNull(reviewSummary) || reviewSummary.isEmpty()) {
            reviewSummaryRepository.save(new ReviewSummary(0F, reviewRequestDTO.getTourId()));
        }

        Review review = reviewMapper.toReview(reviewRequestDTO);
        Review createdReview = reviewRepository.save(review);
        // calculate & update the review summary
        reviewSummaryService.calculateReviewSummaryByTourId(reviewRequestDTO.getTourId());
        return createdReview;
    }

    @Override
    public Review updateReview(Long reviewId, ReviewUpdateRequestDTO reviewUpdateRequestDTO) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new CustomNotFoundException("No review found with that id."));
        reviewMapper.updateReview(reviewUpdateRequestDTO, review);
        // calculate & update the review summary
        reviewSummaryService.calculateReviewSummaryByTourId(review.getTourId());
        return reviewRepository.save(review);
    }

    @Override
    public String toggleLikeReview(String userId, Long reviewId) {
        Assert.notNull(reviewId, "reviewId cannot be null.");
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new CustomNotFoundException("No review found with that id."));
        Optional<ReviewLike> reviewLike = reviewLikeRepository.findReviewLikeByUserIdAndReviewId(userId, reviewId);
        if (reviewLike.isEmpty()) {
            ReviewLike newReviewLike = new ReviewLike(userId, review);
            reviewLikeRepository.save(newReviewLike);
            review.setLikeCount(review.getLikeCount() != null ? review.getLikeCount() + 1 : 1);
            reviewRepository.save(review);
            return "The like has been added.";
        }
        reviewLikeRepository.deleteById(reviewLike.get().getId());
        review.setLikeCount(review.getLikeCount() > 0 ? review.getLikeCount() - 1: review.getLikeCount());
        reviewRepository.save(review);
        return "The like has been removed.";
    }

    @Override
    public PagingApiResponse<Map<String, Object>> getAllReview(ReviewCriteria reviewCriteria) {
        PageRequest pageRequest = CommonPageUtil.getPageRequest(reviewCriteria.getPage(),
                reviewCriteria.getPageSize(), reviewCriteria.getSort());
        Specification<Review> filterBy = andFilterSpecification
                .getAndFilterSpecification(reviewCriteria.getFilters(), List.of("rating"));
        Page<Review> reviewPage = reviewRepository.findAll(filterBy, pageRequest);
        List<Review> reviewList = reviewPage.getContent();
        PageInfo pageInfo = new PageInfo(pageRequest.getPageNumber(), pageRequest.getPageSize(), reviewPage.getTotalElements(), reviewPage.getTotalPages());

        ReviewSummary reviewSummary = null;
        if (!reviewList.isEmpty() && Objects.nonNull(reviewList.get(0).getTourId())) {
            reviewSummary = reviewSummaryService.getReviewSummaryByTourId(reviewList.get(0).getTourId());
        }
        Map<String, Object> data = new HashMap<>();
        data.put("averageRating", reviewSummary != null ? reviewSummary.getAverageRating() : 0);
        data.put("review", reviewList);
        return new PagingApiResponse<>(HttpStatus.OK.value(), data, pageInfo);
    }

    @Override
    public void deleteReview(Long reviewId) {
        reviewRepository.deleteById(reviewId);
    }
}
