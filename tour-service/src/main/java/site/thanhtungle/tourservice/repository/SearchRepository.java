package site.thanhtungle.tourservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import site.thanhtungle.commons.model.dto.PagingDTO;
import site.thanhtungle.tourservice.model.criteria.SearchTourCriteria;

import java.util.List;

@NoRepositoryBean
public interface SearchRepository<T, ID> extends JpaRepository<T, ID> {

    /**
     *  Search tours
     * @param searchTourCriteria
     * @return PagingDTO<T>
     * @example /api/v1/tours/search?q=Start&page=1&pageSize=25&sort=name_sort:asc&priceDiscount=0,1000
     * */
    PagingDTO<T> searchBy(SearchTourCriteria searchTourCriteria);
}
