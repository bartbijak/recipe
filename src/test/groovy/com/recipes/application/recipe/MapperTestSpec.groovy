package com.recipes.application.recipe


import com.recipes.application.recipe.command.IngredientDTO
import com.recipes.application.recipe.command.RecipeDTO
import com.recipes.domain.receipe.Recipe
import com.recipes.domain.receipe.Type
import spock.lang.Specification
import spock.lang.Subject

class MapperTestSpec extends Specification {

    @Subject
    def mapper = new Mapper()

    def "should map create command"() {
        given:
        def command = new RecipeDTO("title", "instruction", 1, Type.VEGETARIAN, [new IngredientDTO("milk", 1.0G, "liter")] as Set<IngredientDTO>)

        when:
        def result = mapper.mapNew(command)

        then:
        result.title == command.title()
        result.instruction == command.instruction()
        result.numberOfServings == command.numberOfServings()
        result.type == command.type()
        result.ingredients.size() == 1
        result.ingredients.first().name == "milk"
        result.ingredients.first().amount == 1.0G
        result.ingredients.first().unit == "liter"
    }

    def "should map update command"() {
        given:
        def id = 1L
        def recipe = Recipe.builder().id(id).build()
        def command = new RecipeDTO("title", "instruction", 1, Type.VEGETARIAN, [new IngredientDTO("milk", 1.0G, "liter")] as Set<IngredientDTO>)

        when:
        def result = mapper.mapExisting(recipe, command)

        then:
        result.id == id
        result.title == command.title()
        result.instruction == command.instruction()
        result.numberOfServings == command.numberOfServings()
        result.type == command.type()
        result.ingredients.size() == 1
        result.ingredients.first().name == "milk"
        result.ingredients.first().amount == 1.0G
        result.ingredients.first().unit == "liter"
    }

}
