package cc.paukner.controllers.v1;

import cc.paukner.api.v1.model.CategoryDto;
import cc.paukner.controllers.CategoryController;
import cc.paukner.services.CategoryService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static cc.paukner.controllers.CategoryController.BASE_URL;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CategoryControllerTest {

    public static final String NAME = "Stevie";

    @Mock
    CategoryService categoryService;

    @InjectMocks
    CategoryController categoryController;

    MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        mockMvc = MockMvcBuilders.standaloneSetup(categoryController).build();
    }

    @Test
    public void listCategories() throws Exception {
        CategoryDto categoryDto1 = CategoryDto.builder().id(1L).name(NAME).build();
        CategoryDto categoryDto2 = CategoryDto.builder().id(2L).name("Bob").build();

        when(categoryService.getAllCategories()).thenReturn(List.of(categoryDto1, categoryDto2));

        mockMvc.perform(get(BASE_URL)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.categories", hasSize(2)));
    }

    @Test
    public void getCategoriesByName() throws Exception {
        CategoryDto categoryDto = CategoryDto.builder().id(1L).name(NAME).build();

        when(categoryService.getCategoryByName(anyString())).thenReturn(categoryDto);

        mockMvc.perform(get(BASE_URL + NAME)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo(NAME)));
    }
}
