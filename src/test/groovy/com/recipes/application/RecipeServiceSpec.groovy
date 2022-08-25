package com.recipes.application

import com.recipes.application.recipe.Mapper
import com.recipes.application.recipe.RecipeService

import com.recipes.application.recipe.command.IngredientDTO
import com.recipes.application.recipe.command.RecipeDTO
import com.recipes.domain.receipe.Recipe
import com.recipes.domain.receipe.Type
import com.recipes.infrastructure.database.sql.repository.RecipeFilteringRepository
import com.recipes.infrastructure.database.sql.repository.RecipeFilters
import com.recipes.infrastructure.database.sql.repository.RecipeRepository
import com.recipes.infrastructure.error.NotFoundException
import spock.lang.Specification
import spock.lang.Subject

class RecipeServiceSpec extends Specification {

    def mapper = Mock(Mapper)
    def repository = Mock(RecipeRepository)
    def filteringRepository = Mock(RecipeFilteringRepository)

    @Subject
    def service = new RecipeService(mapper, repository, filteringRepository)

    def "should save entity"() {
        given:
        def command = new RecipeDTO("title", "instruction", 1, Type.VEGETARIAN, [new IngredientDTO("milk", 1.0G, "liter")] as Set<IngredientDTO>)
        def recipe = Recipe.builder().build()
        and:
        mapper.mapNew(command) >> recipe

        when:
        service.create(command)

        then:
        1 * repository.save(_)
    }

    def "should update entity"() {
        given:
        def id = 1L
        def command = new RecipeDTO("title", "instruction", 1, Type.VEGETARIAN, [new IngredientDTO("milk", 1.0G, "liter")] as Set<IngredientDTO>)
        def recipe = Recipe.builder().build()
        and:
        mapper.mapExisting(_, command) >> recipe

        when:
        service.update(id, command)

        then:
        1 * repository.findById(id) >> Optional.of(recipe)
        1 * repository.save(recipe) >> recipe
    }

    def "should return entity"() {
        given:
        def id = 1L

        when:
        service.get(id)

        then:
        1 * repository.findById(id) >> Optional.of(Recipe.builder().build())
    }

    def "should throw exception entity"() {
        given:
        def id = 1L

        when:
        service.get(id)

        then:
        1 * repository.findById(id) >> Optional.empty()
        thrown(NotFoundException)
    }

    def "should invoke filtering repository"() {
        given:
        def filters = new RecipeFilters()
        def pageNumber = 1
        def pageSize = 1

        when:
        service.filter(filters, pageNumber, pageSize)

        then:
        1 * filteringRepository.findAllByFilters(filters, pageNumber, pageSize)
    }

}
