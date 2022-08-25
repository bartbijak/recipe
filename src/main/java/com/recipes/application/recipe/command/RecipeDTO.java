package com.recipes.application.recipe.command;

import com.recipes.domain.receipe.Type;

import java.util.Set;

public record RecipeDTO(
        String title,
        String instruction,
        int numberOfServings,
        Type type,
        Set<IngredientDTO> ingredients
) {

}
