package com.recipes.api

import com.recipes.api.recipe.RecipesController
import com.recipes.application.recipe.RecipeService
import org.springframework.data.web.PageableHandlerMethodArgumentResolver
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import spock.lang.Specification
import spock.lang.Subject

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

class RecipesControllerSpec extends Specification {

    def service = Mock(RecipeService)

    @Subject
    MockMvc mockMvc = MockMvcBuilders.standaloneSetup(new RecipesController(service))
            .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver()).build()

    def "should return ok status for filter"() {
        given:
        def pageNumber = 1
        def pageSize = 1

        when:
        def response = mockMvc.perform(get("/recipes?pageNumber=$pageNumber&pageSize=$pageSize")
                .contentType("application/json"))

        then:
        1 * service.filter(_, 1, 1)
        response.andExpect(status().isOk())
    }

    def "should return ok status for get "() {
        given:
        def id = 1

        when:
        def response = mockMvc.perform(get("/recipes/$id")
                .contentType("application/json"))

        then:
        1 * service.get(id)
        response.andExpect(status().isOk())
    }

    def "should return accepted status for create"() {
        given:
        def command = """{
            "title": "test", 
            "instruction": "Test",
            "numberOfServings": 10, 
            "type": "VEGETARIAN", 
            "ingredients": [
                {
                    "name": "test2",
                    "amount": 1.22,
                    "unit": "kube2k" 
                }
             ]
        }"""

        when:
        def response = mockMvc.perform(post("/recipes")
                .content(command)
                .contentType("application/json"))

        then:
        1 * service.create(_)
        response.andExpect(status().isOk())
    }

    def "should return accepted status for update"() {
        given:
        def id = 1L
        def command = """{
            "title": "test", 
            "instruction": "Test",
            "numberOfServings": 10, 
            "type": "VEGETARIAN", 
            "ingredients": [
                {
                    "name": "test2",
                    "amount": 1.22,
                    "unit": "kube2k" 
                }
             ]
        }"""

        when:
        def response = mockMvc.perform(put("/recipes/$id")
                .content(command)
                .contentType("application/json"))

        then:
        1 * service.update(id, _)
        response.andExpect(status().isOk())
    }

    def "should return accepted status for delete"() {
        given:
        def id = 1

        when:
        def response = mockMvc.perform(delete("/recipes/$id")
                .contentType("application/json"))

        then:
        1 * service.delete(id)
        response.andExpect(status().isAccepted())
    }

}
