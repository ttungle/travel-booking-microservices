package site.thanhtungle.reviewservice.service;

import org.springframework.transaction.annotation.Transactional;
import site.thanhtungle.reviewservice.model.dto.request.ReviewSummaryRequestDTO;
import site.thanhtungle.reviewservice.model.entity.ReviewSummary;

@Transactional
public interface ReviewSummaryService {

    ReviewSummary createReviewSummary(ReviewSummaryRequestDTO reviewSummaryRequestDTO);

    ReviewSummary calculateReviewSummaryByTourId(Long tourId);

    @Transactional(readOnly = true)
    ReviewSummary getReviewSummaryByTourId(Long tourId);

    void deleteReviewSummary(Long reviewSummaryId);
}
