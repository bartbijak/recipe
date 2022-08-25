package com.recipes.application.recipe;

import com.recipes.application.recipe.command.RecipeDTO;
import com.recipes.domain.receipe.Recipe;
import com.recipes.infrastructure.database.sql.repository.RecipeFilteringRepository;
import com.recipes.infrastructure.database.sql.repository.RecipeFilters;
import com.recipes.infrastructure.database.sql.repository.RecipeRepository;
import com.recipes.infrastructure.database.sql.repository.RecipeResult;
import com.recipes.infrastructure.error.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class RecipeService {

    private final Mapper mapper;
    private final RecipeRepository repository;
    private final RecipeFilteringRepository filteringRepository;

    public RecipeResult filter(RecipeFilters filters, int pageNumber, int pageSize) {
        return filteringRepository.findAllByFilters(filters, pageNumber, pageSize);
    }

    public Recipe get(Long id) {
        return repository.findById(id).orElseThrow(NotFoundException::new);
    }

    public Recipe create(RecipeDTO command) {
        Recipe save = mapper.mapNew(command);
        return repository.save(save);
    }

    public Recipe update(Long id, RecipeDTO command) {
        return repository.findById(id)
                .map(entity -> mapper.mapExisting(entity, command))
                .map(repository::save)
                .orElseThrow(NotFoundException::new);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

}
