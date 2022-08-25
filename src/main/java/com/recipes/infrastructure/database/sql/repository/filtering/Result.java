package com.recipes.infrastructure.database.sql.repository.filtering;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Result<T> {

    long totalCount;
    int totalPages;
    int currentPage;
    List<T> data;

}
