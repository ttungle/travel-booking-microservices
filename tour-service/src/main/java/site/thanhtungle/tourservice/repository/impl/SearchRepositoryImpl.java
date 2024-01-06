package site.thanhtungle.tourservice.repository.impl;

import jakarta.persistence.EntityManager;
import org.hibernate.search.engine.search.query.SearchResult;
import org.hibernate.search.mapper.orm.Search;
import org.hibernate.search.mapper.orm.session.SearchSession;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.transaction.annotation.Transactional;
import site.thanhtungle.commons.model.dto.PagingDTO;
import site.thanhtungle.commons.model.response.success.PageInfo;
import site.thanhtungle.tourservice.model.criteria.SearchTourCriteria;
import site.thanhtungle.tourservice.repository.SearchRepository;
import site.thanhtungle.tourservice.util.PageUtil;

import java.time.Instant;
import java.util.List;

@Transactional
public class SearchRepositoryImpl<T, ID> extends SimpleJpaRepository<T, ID> implements SearchRepository<T, ID> {

    private final EntityManager entityManager;

    public SearchRepositoryImpl(JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager) {
        super(entityInformation, entityManager);
        this.entityManager = entityManager;
    }

    public SearchRepositoryImpl(Class<T> domainClass, EntityManager entityManager) {
        super(domainClass, entityManager);
        this.entityManager = entityManager;
    }

    @Override
    public PagingDTO<T> searchBy(SearchTourCriteria searchTourCriteria) {
        SearchResult<T> result = getSearchResult(searchTourCriteria);

        PageInfo pageInfo = new PageInfo(
                searchTourCriteria.getPage(),
                searchTourCriteria.getPageSize(),
                result.total().hitCount());
        return new PagingDTO<>(result.hits(), pageInfo);
    }

    private SearchResult<T> getSearchResult(SearchTourCriteria searchTourCriteria) {
        int offset = (searchTourCriteria.getPage() - 1) * searchTourCriteria.getPageSize();
        SearchSession searchSession = Search.session(entityManager);

        return searchSession.search(getDomainClass())
                .where(f -> f.bool(b -> {
                        if (searchTourCriteria.getQ() != null) {
                            b.must(f.match().fields(searchTourCriteria.getFields()).matching(searchTourCriteria.getQ()));
                        }

                        if (searchTourCriteria.getCategoryId() != null) {
                            b.filter(f.match().field("category.id")
                                    .matching(searchTourCriteria.getCategoryId()));
                        }

                        if (searchTourCriteria.getStartLocation() != null) {
                            b.filter(f.match().field("startLocation")
                                    .matching(searchTourCriteria.getStartLocation()));
                        }

                        if (searchTourCriteria.getPriceDiscount() != null) {
                            String[] splitValue= searchTourCriteria.getPriceDiscount().split(",");
                            b.filter(f.range().field("priceDiscount")
                                    .between(Float.parseFloat(splitValue[0]), Float.parseFloat(splitValue[1])));
                        }

                        if (searchTourCriteria.getRatingAverage() != null) {
                            String[] splitValue= searchTourCriteria.getRatingAverage().split(",");
                            b.filter(f.range().field("ratingAverage")
                                    .between(Float.parseFloat(splitValue[0]), Float.parseFloat(splitValue[1])));
                        }

                        if (searchTourCriteria.getStartDate() != null) {
                            String[] splitValue= searchTourCriteria.getStartDate().split(",");
                            b.filter(f.range().field("startDate")
                                    .between(Instant.parse(splitValue[0]), Instant.parse(splitValue[1])));
                        }
                    })

                )
                .sort(f -> {
                    if (searchTourCriteria.getSort() == null) return f.field("id").asc();
                    List<String> sortList = PageUtil.getStringSort(searchTourCriteria.getSort());
                    return switch (sortList.get(1)) {
                        case "asc" -> f.field(sortList.get(0)).asc();
                        case "desc" -> f.field(sortList.get(0)).desc();
                        default -> f.field("id").asc();
                    };
                })
                .fetch(offset, searchTourCriteria.getPageSize());
    }
}