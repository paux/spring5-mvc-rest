package cc.paukner.api.v1.mapper;

import cc.paukner.api.v1.model.CustomerDto;
import cc.paukner.domain.Customer;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CustomerMapperTest {

    public static final long ID = 1L;
    public static final String FIRST_NAME = "Stevie";
    public static final String LAST_NAME = "Paukner";

    CustomerMapper customerMapper = CustomerMapper.INSTANCE;

    @Test
    public void customerToCustomerDto() {
        // given
        Customer customer = Customer.builder().id(ID).firstName(FIRST_NAME).lastName(LAST_NAME).build();

        // when
        CustomerDto customerDto = customerMapper.customerToCustomerDto(customer);

        // then
        assertEquals(Long.valueOf(ID), customerDto.getId());
        assertEquals(FIRST_NAME, customerDto.getFirstName());
        assertEquals(LAST_NAME, customerDto.getLastName());
    }
}
