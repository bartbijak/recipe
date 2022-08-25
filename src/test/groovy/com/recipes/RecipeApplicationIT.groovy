package com.recipes

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import spock.lang.Specification

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest
@AutoConfigureMockMvc
class RecipeApplicationIT extends Specification {

    @Autowired MockMvc mvc

    def "should save and return given recipe"() {
        given:
        def specificName = "specificNameTitle" + System.currentTimeMillis()
        def command = """{ 
            "title": "$specificName", 
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

        when: "creating new recipe"
        mvc.perform(post("/recipes")
                .content(command)
                .contentType("application/json"))
                .andExpect(status().isOk())

        and: "getting list of 1000 recipes"
        def result = mvc.perform(get("/recipes?pageNumber=1&pageSize=1000")
                .contentType("application/json")).andReturn()

        then: "expect that list contains created recipe with specific name"
        result.response.contentAsString.contains(specificName)
    }

}
