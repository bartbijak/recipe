package com.recipes.domain.receipe;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import java.io.Serial;
import java.io.Serializable;
import java.util.Set;

@Getter
@Entity
@Table(name = "recipes")
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Recipe implements Serializable {

    @Serial
    private static final long serialVersionUID = -7044123190745766934L;

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @Setter
    @Column(name = "title", nullable = false)
    private String title;

    @Setter
    @Column(name = "instruction", nullable = false)
    private String instruction;

    @Setter
    @Column(name = "number_of_servings", nullable = false)
    private int numberOfServings;

    @Setter
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private Type type;

    @Setter
    @ElementCollection
    @CollectionTable(name = "recipe_ingredients", joinColumns = @JoinColumn(name = "recipe_id"))
    private Set<Ingredient> ingredients;

    public Recipe(String title, String instruction, int numberOfServings, Type type, Set<Ingredient> ingredients) {
        this.title = title;
        this.instruction = instruction;
        this.numberOfServings = numberOfServings;
        this.type = type;
        this.ingredients = ingredients;
    }

}