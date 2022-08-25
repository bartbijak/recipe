package com.recipes.api.recipe;

import com.recipes.application.recipe.RecipeService;
import com.recipes.application.recipe.command.RecipeDTO;
import com.recipes.domain.receipe.Recipe;
import com.recipes.domain.receipe.Type;
import com.recipes.infrastructure.database.sql.repository.RecipeFilters;
import com.recipes.infrastructure.database.sql.repository.RecipeOrderBy;
import com.recipes.infrastructure.database.sql.repository.RecipeResult;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping("/recipes")
@RequiredArgsConstructor
public class RecipesController {

    private final RecipeService service;

    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Recipe founded",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = Recipe.class)
                            )
                    }),
            @ApiResponse(
                    responseCode = "404",
                    description = "Recipe not found"
            )
    })
    @GetMapping("/{id}")
    public Recipe get(@PathVariable long id) {
        return service.get(id);
    }

    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Recipes founded",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = RecipeResult.class)
                            )
                    })
    })
    @GetMapping
    public RecipeResult getAll(@RequestParam(required = false) Type type,
                               @RequestParam(required = false) Set<String> includedIngredients,
                               @RequestParam(required = false) Set<String> excludedIngredients,
                               @RequestParam(required = false) Integer numberOfServings,
                               @RequestParam(required = false) String fullTextSearch,
                               @RequestParam(required = false) RecipeOrderBy orderBy,
                               @RequestParam(required = false) Integer pageSize,
                               @RequestParam(required = false) Integer pageNumber) {
        return service.filter(
                new RecipeFilters(type, includedIngredients, excludedIngredients, numberOfServings, fullTextSearch, orderBy),
                pageNumber, pageSize
        );
    }

    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Recipe created",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = Recipe.class)
                            )
                    }),
            @ApiResponse(
                    responseCode = "400",
                    description = "Bad request"
            )
    })
    @PostMapping
    public Recipe create(@RequestBody RecipeDTO command) {
        return service.create(command);
    }

    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Recipe updated",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = Recipe.class)
                            )
                    }),
            @ApiResponse(
                    responseCode = "400",
                    description = "Bad request"
            )
    })
    @PutMapping("/{id}")
    public Recipe update(@PathVariable long id, @RequestBody RecipeDTO command) {
        return service.update(id, command);
    }

    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "202",
                    description = "Recipe deleted",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = Recipe.class)
                            )
                    })
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void delete(@PathVariable long id) {
        service.delete(id);
    }

}
