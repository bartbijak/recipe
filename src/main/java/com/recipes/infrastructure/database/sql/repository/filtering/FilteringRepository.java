package com.recipes.infrastructure.database.sql.repository.filtering;

import lombok.AllArgsConstructor;
import org.apache.commons.collections.CollectionUtils;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
public abstract class FilteringRepository<T, S extends Filters> {

    protected static final int DEFAULT_PAGE_SIZE = 10;
    public static final int DEFAULT_PAGE_NUMBER= 1;

    private final EntityManager entityManager;

    public abstract Result<T> findAllByFilters(S filters, Integer pageNumber, Integer pageSize);

    public void applyObjectFilter(CriteriaBuilder builder, List<Predicate> predicates, Object id, Path<String> path) {
        if (id != null) {
            predicates.add(builder.equal(path, id));
        }
    }

    public void applyInFilter(List<Predicate> predicates, Set<? extends Object> set, Path<String> path) {
        if (CollectionUtils.isNotEmpty(set)) {
            predicates.add(path.in(set));
        }
    }

    public void applyNotInFilter(List<Predicate> predicates, Set<? extends Object> set, Path<String> path) {
        if (CollectionUtils.isNotEmpty(set)) {
            predicates.add(path.in(set).not());
        }
    }

    public CriteriaQuery<T> applyOrdering(S filters, CriteriaBuilder builder, CriteriaQuery<T> query, Root<T> root) {
        if (filters.getOrderBy() != null) {
            return filters.getOrderBy().getSecondName().equals("")
                    ? applyFirstLevelSort(filters, builder, query, root)
                    : applySecondLevelSort(filters, builder, query, root);
        } else {
            return query;
        }
    }

    private CriteriaQuery<T> applyFirstLevelSort(S filters, CriteriaBuilder builder, CriteriaQuery<T> query, Root<T> root) {
        return filters.getOrderBy().isAscending()
                ? query.orderBy(builder.asc(root.get(filters.getOrderBy().getName())), builder.asc(root.get("id")))
                : query.orderBy(builder.desc(root.get(filters.getOrderBy().getName())), builder.desc(root.get("id")));
    }

    private CriteriaQuery<T> applySecondLevelSort(S filters, CriteriaBuilder builder, CriteriaQuery<T> query, Root<T> root) {
        return filters.getOrderBy().isAscending()
                ? query.orderBy(builder.asc(root.get(filters.getOrderBy().getName()).get(filters.getOrderBy().getSecondName())), builder.asc(root.get("id")))
                : query.orderBy(builder.desc(root.get(filters.getOrderBy().getName()).get(filters.getOrderBy().getSecondName())), builder.desc(root.get("id")));
    }

    public Result<T> applyPaginationAndGetResult(Integer pageNumber, Integer pageSize, CriteriaQuery<T> query) {
        return pageNumber != null && pageSize != null
                ? getPaginationResult(pageNumber, pageSize, query)
                : getPaginationResult(DEFAULT_PAGE_NUMBER, DEFAULT_PAGE_SIZE, query);
    }

    private Result<T> getPaginationResult(Integer pageNumber, Integer pageSize, CriteriaQuery<T> query) {
        List<T> resultList = entityManager.createQuery(query)
                .setFirstResult((pageNumber - 1) * pageSize)
                .setMaxResults(pageSize).getResultList();
        List<T> allRecords = entityManager.createQuery(query).getResultList();

        return new Result<>(allRecords.size(), calculateNumberOfPages(pageSize, allRecords), pageNumber, resultList);
    }

    public int calculateNumberOfPages(Integer pageSize, List<T> result) {
        return (int) Math.ceil((double) result.size() / pageSize);
    }

}
