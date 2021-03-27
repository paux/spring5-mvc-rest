package cc.paukner.controllers;

import cc.paukner.api.v1.model.CustomerDto;
import cc.paukner.services.CustomerService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CustomerControllerTest {

    public static final String LAST_NAME = "Paukner";
    public static final String FIRST_NAME = "Stevie";

    @Mock
    CustomerService customerService;

    @InjectMocks
    CustomerController customerController;

    MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        mockMvc = MockMvcBuilders.standaloneSetup(customerController).build();
    }

    @Test
    public void listCustomers() throws Exception {
        CustomerDto customerDto1 = CustomerDto.builder().id(1L).firstName(FIRST_NAME).lastName(LAST_NAME).build();
        CustomerDto customerDto2 = CustomerDto.builder().id(2L).firstName("Bob").lastName("Builder").build();

        when(customerService.getAllCustomers()).thenReturn(List.of(customerDto1, customerDto2));

        mockMvc.perform(get("/api/v1/customers/")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customers", hasSize(2)));
    }

    @Test
    public void getCustomersByName() throws Exception {
        CustomerDto customerDto = CustomerDto.builder().id(1L).firstName(FIRST_NAME).lastName(LAST_NAME).build();

        when(customerService.getCustomerByName(anyString(), anyString())).thenReturn(customerDto);

        mockMvc.perform(get("/api/v1/customers/" + LAST_NAME + "/" + FIRST_NAME)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", equalTo(FIRST_NAME)))
                .andExpect(jsonPath("$.lastName", equalTo(LAST_NAME)));
    }
}
