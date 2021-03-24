package cc.paukner.services;

import cc.paukner.api.v1.mapper.CategoryMapper;
import cc.paukner.api.v1.model.CategoryDto;
import cc.paukner.domain.Category;
import cc.paukner.repositories.CategoryRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

public class CategoryServiceTest {

    public static final Long ID = 2L;
    public static final String NAME = "Stevie";

    CategoryService categoryService;

    @Mock
    CategoryRepository categoryRepository;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        categoryService = new DefaultCategoryService(CategoryMapper.INSTANCE, categoryRepository);
    }

    @Test
    public void getAllCategories() {

        //given
        List<Category> categories = Arrays.asList(new Category(), new Category(), new Category());

        when(categoryRepository.findAll()).thenReturn(categories);

        // when
        List<CategoryDto> categoryDtos = categoryService.getAllCategories();

        // then
        assertEquals(3, categories.size());
    }

    @Test
    public void getCategoryByName() throws Exception {

        // given
        Category category = Category.builder().id(ID).name(NAME).build();

        when(categoryRepository.findByName(anyString())).thenReturn(category);

        // when
        CategoryDto categoryDto = categoryService.getCategoryByName(NAME);

        // then
        assertEquals(ID, categoryDto.getId());
        assertEquals(NAME, categoryDto.getName());
    }
}
