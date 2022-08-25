package com.recipes.infrastructure.database.sql.repository;

import com.recipes.domain.receipe.Type;
import com.recipes.infrastructure.database.sql.repository.filtering.Filters;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Set;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RecipeFilters extends Filters {

    private Type type;
    private Set<String> includedIngredients;
    private Set<String> excludedIngredients;
    private Integer numberOfServings;

    private String fullTextSearchInput;

    private RecipeOrderBy orderBy;

}
