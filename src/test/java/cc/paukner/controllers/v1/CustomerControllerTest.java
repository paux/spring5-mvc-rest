package cc.paukner.controllers.v1;

import cc.paukner.api.v1.model.CustomerDto;
import cc.paukner.controllers.RestResponseEntityExceptionHandler;
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

import static cc.paukner.controllers.v1.CustomerController.BASE_URL;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CustomerControllerTest extends AbstractRestControllerTest {

    public static final String LAST_NAME = "Paukner";
    public static final String FIRST_NAME = "Stevie";
    public static final String CUSTOMER_API = BASE_URL + 1;

    @Mock
    CustomerService customerService;

    @InjectMocks
    CustomerController customerController;

    MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        mockMvc = MockMvcBuilders.standaloneSetup(customerController)
                .setControllerAdvice(new RestResponseEntityExceptionHandler())
                .build();
    }

    @Test
    public void listCustomers() throws Exception {
        CustomerDto customerDto1 = CustomerDto.builder()
                .id(1L)
                .firstName(FIRST_NAME)
                .lastName(LAST_NAME)
                .build();
        CustomerDto customerDto2 = CustomerDto.builder()
                .id(2L)
                .firstName("Bob")
                .lastName("Builder")
                .build();

        when(customerService.getAllCustomers()).thenReturn(List.of(customerDto1, customerDto2));

        mockMvc.perform(get(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customers", hasSize(2)));
    }

    @Test
    public void getCustomersByName() throws Exception {
        CustomerDto customerDto = CustomerDto.builder()
                .id(1L)
                .firstName(FIRST_NAME)
                .lastName(LAST_NAME).build();

        when(customerService.getCustomerByName(anyString(), anyString())).thenReturn(customerDto);

        mockMvc.perform(get(BASE_URL + LAST_NAME + "/" + FIRST_NAME)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.first_name", equalTo(FIRST_NAME)))
                .andExpect(jsonPath("$.last_name", equalTo(LAST_NAME)));
    }

    @Test
    public void createCustomer() throws Exception {
        CustomerDto customerDto = CustomerDto.builder()
                .id(1L)
                .firstName(FIRST_NAME)
                .lastName(LAST_NAME)
                .build();
        CustomerDto returnDto = CustomerDto.builder()
                .firstName(customerDto.getFirstName())
                .lastName(customerDto.getLastName())
                .customerUrl(CUSTOMER_API)
                .build();

        when(customerService.createCustomer(customerDto)).thenReturn(returnDto);

        mockMvc.perform(post(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(customerDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.first_name", equalTo(FIRST_NAME)))
                .andExpect(jsonPath("$.last_name", equalTo(LAST_NAME)))
                .andExpect(jsonPath("$.customer_url", equalTo(CUSTOMER_API)));
    }

    @Test
    public void updateCustomer() throws Exception {
        CustomerDto customerDto = CustomerDto.builder()
                .id(1L)
                .firstName(FIRST_NAME)
                .lastName(LAST_NAME)
                .build();
        CustomerDto returnDto = CustomerDto.builder()
                .firstName(customerDto.getFirstName())
                .lastName(customerDto.getLastName())
                .customerUrl(CUSTOMER_API)
                .build();

        when(customerService.updateCustomer(anyLong(), any(CustomerDto.class))).thenReturn(returnDto);

        mockMvc.perform(put(CUSTOMER_API)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(customerDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.first_name", equalTo(FIRST_NAME)))
                .andExpect(jsonPath("$.last_name", equalTo(LAST_NAME)))
                .andExpect(jsonPath("$.customer_url", equalTo(CUSTOMER_API)));
    }

    @Test
    public void patchCustomer() throws Exception {
        CustomerDto customerDto = CustomerDto.builder().firstName(FIRST_NAME).build();
        CustomerDto returnDto = CustomerDto.builder()
                .firstName(customerDto.getFirstName())
                .lastName(LAST_NAME)
                .customerUrl(CUSTOMER_API)
                .build();

        when(customerService.patchCustomer(anyLong(), any(CustomerDto.class))).thenReturn(returnDto);

        mockMvc.perform(patch(CUSTOMER_API)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(customerDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.first_name", equalTo(FIRST_NAME)))
                .andExpect(jsonPath("$.last_name", equalTo(LAST_NAME)))
                .andExpect(jsonPath("$.customer_url", equalTo(CUSTOMER_API)));
    }

    @Test
    public void deleteCustomer() throws Exception {
        mockMvc.perform(delete(CUSTOMER_API)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(customerService).deleteCustomerById(anyLong());
    }
}
