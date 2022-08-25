package com.recipes.infrastructure.database.sql.repository;

import com.recipes.domain.receipe.Ingredient;
import com.recipes.domain.receipe.Recipe;
import com.recipes.infrastructure.database.sql.repository.filtering.FilteringRepository;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Component
public class RecipeFilteringRepository extends FilteringRepository<Recipe, RecipeFilters> {

    private final EntityManager entityManager;

    public RecipeFilteringRepository(EntityManager entityManager) {
        super(entityManager);
        this.entityManager = entityManager;
    }

    @Override
    public RecipeResult findAllByFilters(RecipeFilters filters, Integer pageNumber, Integer pageSize) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Recipe> query = builder.createQuery(Recipe.class);

        Root<Recipe> root = query.from(Recipe.class);

        Join<Recipe, Ingredient> ingredientJoin = root.join("ingredients");
        List<Predicate> basePredicates = new ArrayList<>();

        applyObjectFilter(builder, basePredicates, filters.getType(), root.get("type"));
        applyObjectFilter(builder, basePredicates, filters.getNumberOfServings(), root.get("numberOfServings"));
        applyInFilter(basePredicates, filters.getIncludedIngredients(), ingredientJoin.get("name"));
        applyNotInFilter(basePredicates, filters.getExcludedIngredients(), ingredientJoin.get("name"));
        applyFullTextSearchImitation(builder, basePredicates, filters.getFullTextSearchInput(), root.get("instruction"));

        applyOrdering(filters, builder, query, root).where(basePredicates.toArray(new Predicate[0]));

        return new RecipeResult(applyPaginationAndGetResult(pageNumber, pageSize, query));
    }

    //TODO: this type of a query can be executed using ElasticSearch
    public void applyFullTextSearchImitation(CriteriaBuilder builder, List<Predicate> predicates,
                                             String name, Path<String> path1) {
        if (name != null) {
            predicates.add(
                    builder.like(path1, "%" + name + "%")
            );
        }
    }

}
