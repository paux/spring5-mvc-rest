package cc.paukner.services;

import cc.paukner.api.v1.mapper.CustomerMapper;
import cc.paukner.api.v1.model.CustomerDto;
import cc.paukner.domain.Customer;
import cc.paukner.repositories.CustomerRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

public class CustomerServiceTest {

    public static final Long ID = 2L;
    public static final String FIRST_NAME = "Stevie";
    public static final String LAST_NAME = "Paukner";

    CustomerService customerService;

    @Mock
    CustomerRepository customerRepository;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        customerService = new DefaultCustomerService(CustomerMapper.INSTANCE, customerRepository);
    }

    @Test
    public void getAllCustomers() {
        //given
        List<Customer> customers = Arrays.asList(new Customer(), new Customer(), new Customer());

        when(customerRepository.findAll()).thenReturn(customers);

        // when
        List<CustomerDto> customerDtos = customerService.getAllCustomers();

        // then
        assertEquals(3, customerDtos.size());
    }
}
