package site.thanhtungle.reviewservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import site.thanhtungle.reviewservice.model.dto.request.ReviewRequestDTO;
import site.thanhtungle.reviewservice.model.dto.request.ReviewUpdateRequestDTO;
import site.thanhtungle.reviewservice.model.entity.Review;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ReviewMapper {

    Review toReview(ReviewRequestDTO reviewRequestDTO);

    void updateReview(ReviewUpdateRequestDTO reviewUpdateRequestDTO, @MappingTarget Review review);
}
