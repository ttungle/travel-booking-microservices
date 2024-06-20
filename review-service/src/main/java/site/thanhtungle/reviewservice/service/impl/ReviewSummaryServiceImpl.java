package site.thanhtungle.reviewservice.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import site.thanhtungle.commons.exception.CustomNotFoundException;
import site.thanhtungle.reviewservice.mapper.ReviewSummaryMapper;
import site.thanhtungle.reviewservice.model.dto.request.ReviewSummaryRequestDTO;
import site.thanhtungle.reviewservice.model.entity.ReviewSummary;
import site.thanhtungle.reviewservice.repository.ReviewRepository;
import site.thanhtungle.reviewservice.repository.ReviewSummaryRepository;
import site.thanhtungle.reviewservice.service.ReviewSummaryService;

@Service
@AllArgsConstructor
public class ReviewSummaryServiceImpl implements ReviewSummaryService {

    private final ReviewSummaryRepository reviewSummaryRepository;
    private final ReviewRepository reviewRepository;
    private final ReviewSummaryMapper reviewSummaryMapper;

    @Override
    public ReviewSummary createReviewSummary(ReviewSummaryRequestDTO reviewSummaryRequestDTO) {
        ReviewSummary reviewSummary = reviewSummaryMapper.toReviewSummary(reviewSummaryRequestDTO);
        return reviewSummaryRepository.save(reviewSummary);
    }

    @Override
    public ReviewSummary calculateReviewSummaryByTourId(Long tourId) {
        ReviewSummary reviewSummary = reviewSummaryRepository.findByTourId(tourId)
                .orElseThrow(() -> new CustomNotFoundException("No review summary found with tourId: " + tourId));
        Float averageRating = reviewRepository.getAverageRating(tourId);
        reviewSummary.setAverageRating(averageRating);
        return reviewSummaryRepository.save(reviewSummary);
    }

    @Override
    public ReviewSummary getReviewSummaryByTourId(Long tourId) {
        Assert.notNull(tourId, "Cannot get reviewSummary because tourId is null.");
        return reviewSummaryRepository.findByTourId(tourId)
                .orElseThrow(() -> new CustomNotFoundException("No reviewSummary found with tourId: " + tourId));
    }

    @Override
    public void deleteReviewSummary(Long reviewSummaryId) {
        reviewSummaryRepository.findById(reviewSummaryId)
                .orElseThrow(() -> new CustomNotFoundException("No review summary found with that id."));
    }
}
