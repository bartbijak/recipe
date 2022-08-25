package com.recipes.application.recipe;

import com.recipes.application.recipe.command.IngredientDTO;
import com.recipes.application.recipe.command.RecipeDTO;
import com.recipes.domain.receipe.Ingredient;
import com.recipes.domain.receipe.Recipe;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class Mapper {

    Recipe mapNew(RecipeDTO command) {
        return new Recipe(
                command.title(),
                command.instruction(),
                command.numberOfServings(),
                command.type(),
                toDomainObject(command.ingredients())
        );
    }

    Recipe mapExisting(Recipe recipe, RecipeDTO command) {
        recipe.setTitle(command.title());
        recipe.setInstruction(command.instruction());
        recipe.setNumberOfServings(command.numberOfServings());
        recipe.setType(command.type());
        recipe.setIngredients(toDomainObject(command.ingredients()));
        return recipe;
    }

    private Set<Ingredient> toDomainObject(Set<IngredientDTO> dtos) {
        return dtos.stream()
                .map(this::mapDTOToIngredient)
                .collect(Collectors.toSet());
    }

    private Ingredient mapDTOToIngredient(IngredientDTO dto) {
        return new Ingredient(dto.name(), dto.amount(), dto.unit());
    }

}
