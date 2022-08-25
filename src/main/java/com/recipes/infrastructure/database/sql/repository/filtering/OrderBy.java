package com.recipes.infrastructure.database.sql.repository.filtering;

public interface OrderBy {

    default String getName() {
        return "id";
    }

    default String getSecondName() {
        return "";
    }

    default boolean isAscending() {
        return false;
    }

}
