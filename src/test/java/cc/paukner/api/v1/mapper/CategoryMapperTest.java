package cc.paukner.api.v1.mapper;

import cc.paukner.api.v1.model.CategoryDto;
import cc.paukner.domain.Category;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CategoryMapperTest {

    public static final String NAME = "Joe";
    public static final long ID = 1L;

    CategoryMapper categoryMapper = CategoryMapper.INSTANCE;

    @Test
    public void testCategoryToCategoryDto() throws Exception {
        // given
        Category category = Category.builder().name(NAME).id(ID).build();

        // when
        CategoryDto categoryDto = categoryMapper.categoryToCategoryDto(category);

        // then
        assertEquals(Long.valueOf(ID), categoryDto.getId());
        assertEquals(NAME, categoryDto.getName());
    }
}
