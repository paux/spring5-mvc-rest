package cc.paukner.api.v1.services;

import cc.paukner.api.v1.model.CategoryDto;

import java.util.List;

public interface CategoryService {

    List<CategoryDto> getAllCategories();

    CategoryDto getCategoryByName(String name);
}
