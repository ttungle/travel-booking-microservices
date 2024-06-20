package site.thanhtungle.reviewservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import site.thanhtungle.reviewservice.model.dto.request.ReviewSummaryRequestDTO;
import site.thanhtungle.reviewservice.model.entity.ReviewSummary;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ReviewSummaryMapper {

    ReviewSummary toReviewSummary(ReviewSummaryRequestDTO reviewSummaryRequestDTO);
}
