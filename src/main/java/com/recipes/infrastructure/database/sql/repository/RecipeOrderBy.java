package com.recipes.infrastructure.database.sql.repository;

import com.recipes.infrastructure.database.sql.repository.filtering.OrderBy;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public
enum RecipeOrderBy implements OrderBy {

    TITLE_ASC("title", "", true),
    TITLE_DESC("title", "", false),
    AMOUNT_ASC("description", "", true),
    AMOUNT_DESC("description", "", false),
    DATE_ASC("title", "created_at", true),
    DATE_DESC("title", "created_at", false);

    private final String name;
    private final String secondName;
    private final boolean ascending;

}
