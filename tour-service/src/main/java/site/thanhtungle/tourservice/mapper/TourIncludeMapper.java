package site.thanhtungle.tourservice.mapper;

import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;
import site.thanhtungle.tourservice.model.dto.request.tourinclude.TourIncludeRequestDTO;
import site.thanhtungle.tourservice.model.dto.response.tourinclude.TourIncludeResponseDTO;
import site.thanhtungle.tourservice.model.entity.Tour;
import site.thanhtungle.tourservice.model.entity.TourInclude;
import site.thanhtungle.tourservice.repository.TourRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        injectionStrategy = InjectionStrategy.FIELD
)
public abstract class TourIncludeMapper {

    @Autowired
    private TourRepository tourRepository;

    @Mapping(target = "tours", source = "tourIds", qualifiedByName = "toEntityTourListFromId")
    public abstract TourInclude toEntityTourInclude(TourIncludeRequestDTO tourIncludeRequestDTO);

    public abstract TourIncludeResponseDTO toTourIncludeResponseDTO(TourInclude tourInclude);

    public void updateTourInclude(TourInclude tourInclude, TourIncludeRequestDTO tourIncludeRequestDTO) {
        if ( tourIncludeRequestDTO == null ) {
            return;
        }

        if ( tourInclude.getTours() != null ) {
            Set<Tour> set = toEntityTourListFromId( tourIncludeRequestDTO.getTourIds() );
            if ( set != null ) {
                tourInclude.getTours().addAll( set );
            }
        }
        else {
            Set<Tour> set = toEntityTourListFromId( tourIncludeRequestDTO.getTourIds() );
            if ( set != null ) {
                tourInclude.setTours( set );
            }
        }
        if ( tourIncludeRequestDTO.getContent() != null ) {
            tourInclude.setContent( tourIncludeRequestDTO.getContent() );
        }
    }

    @Named("toEntityTourListFromId")
    protected Set<Tour> toEntityTourListFromId(List<Long> tourIdList) {
        if (tourIdList == null || tourIdList.isEmpty()) return null;
        List<Tour> tour = tourRepository.findTourByIdIn(tourIdList);
        return new HashSet<>(tour);
    }
}
