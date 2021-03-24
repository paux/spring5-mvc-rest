package cc.paukner.services;

import cc.paukner.api.v1.mapper.CategoryMapper;
import cc.paukner.api.v1.model.CategoryDto;
import cc.paukner.repositories.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DefaultCategoryService implements CategoryService {

    private final CategoryMapper categoryMapper;
    private final CategoryRepository categoryRepository;

    public DefaultCategoryService(CategoryMapper categoryMapper, CategoryRepository categoryRepository) {
        this.categoryMapper = categoryMapper;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<CategoryDto> getAllCategories() {
        return categoryRepository.findAll()
                .stream()
                .map(categoryMapper::categoryToCategoryDto)
                .collect(Collectors.toList());
    }

    @Override
    public CategoryDto getCategoryByName(String name) {
        return categoryMapper.categoryToCategoryDto(categoryRepository.findByName(name));
    }
}
