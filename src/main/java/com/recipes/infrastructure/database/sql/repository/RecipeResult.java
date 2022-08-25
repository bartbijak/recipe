package com.recipes.infrastructure.database.sql.repository;

import com.recipes.domain.receipe.Recipe;
import com.recipes.infrastructure.database.sql.repository.filtering.Result;
import lombok.Getter;

@Getter
public class RecipeResult extends Result<Recipe> {

    public RecipeResult(Result<Recipe> result) {
        super(result.getTotalCount(), result.getTotalPages(), result.getCurrentPage(), result.getData());
    }

}
