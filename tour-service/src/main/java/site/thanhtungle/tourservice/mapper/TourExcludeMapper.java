package site.thanhtungle.tourservice.mapper;

import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;
import site.thanhtungle.tourservice.model.dto.request.tourexclude.TourExcludeRequestDTO;
import site.thanhtungle.tourservice.model.dto.response.tourexclude.TourExcludeResponseDTO;
import site.thanhtungle.tourservice.model.entity.Tour;
import site.thanhtungle.tourservice.model.entity.TourExclude;
import site.thanhtungle.tourservice.repository.TourRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        injectionStrategy = InjectionStrategy.FIELD
)
public abstract class TourExcludeMapper {

    @Autowired
    private TourRepository tourRepository;

    @Mapping(target = "tours", source = "tourIds", qualifiedByName = "toEntityTourListFromId")
    public abstract TourExclude toEntityTourExclude(TourExcludeRequestDTO tourExcludeRequestDTO);

    public abstract TourExcludeResponseDTO toTourExcludeResponseDTO(TourExclude tourExclude);

    public void updateTourExclude(TourExclude tourExclude, TourExcludeRequestDTO tourExcludeRequestDTO) {
        if ( tourExcludeRequestDTO == null ) {
            return;
        }

        if ( tourExclude.getTours() != null ) {
            Set<Tour> set = toEntityTourListFromId( tourExcludeRequestDTO.getTourIds() );
            if ( set != null ) {
                tourExclude.getTours().addAll( set );
            }
        }
        else {
            Set<Tour> set = toEntityTourListFromId( tourExcludeRequestDTO.getTourIds() );
            if ( set != null ) {
                tourExclude.setTours( set );
            }
        }
        if ( tourExcludeRequestDTO.getContent() != null ) {
            tourExclude.setContent( tourExcludeRequestDTO.getContent() );
        }
    }

    @Named("toEntityTourListFromId")
    protected Set<Tour> toEntityTourListFromId(List<Long> tourIdList) {
        if (tourIdList == null || tourIdList.isEmpty()) return null;
        List<Tour> tours = tourRepository.findTourByIdIn(tourIdList);
        return new HashSet<>(tours);
    }
}
