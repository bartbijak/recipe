package com.recipes.application.recipe.command;

import java.math.BigDecimal;

public record IngredientDTO(
        String name,
        BigDecimal amount,
        String unit
) {

}
