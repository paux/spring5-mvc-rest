package cc.paukner.services;

import cc.paukner.api.v1.mapper.CustomerMapper;
import cc.paukner.api.v1.model.CustomerDto;
import cc.paukner.bootstrap.Bootstrap;
import cc.paukner.domain.Customer;
import cc.paukner.repositories.CategoryRepository;
import cc.paukner.repositories.CustomerRepository;
import cc.paukner.repositories.VendorRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest // only brings up repos, no services, no controllers
public class DefaultCustomerServiceIT {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    VendorRepository vendorRepository;

    CustomerService customerService;

    @Before
    public void setUp() throws Exception {
        System.out.println("Loading customer data: " + customerRepository.findAll().size());

        // prepare data for testing
        new Bootstrap(categoryRepository, customerRepository, vendorRepository).run();

        customerService = new DefaultCustomerService(CustomerMapper.INSTANCE, customerRepository);
    }

    @Test
    public void patchCustomerUpdateFirstName() throws Exception {
        String updatedName = "updatedName";
        long id = getFirstCustomerId();

        Customer originalCustomer = customerRepository.getOne(id);
        assertNotNull(originalCustomer);
        String originalFirstName = originalCustomer.getFirstName();
        String originalLastName = originalCustomer.getLastName();

        CustomerDto customerDto = CustomerDto.builder().firstName(updatedName).build();

        customerService.patchCustomer(id, customerDto);

        Customer updatedCustomer = customerRepository.findById(id).get();

        assertNotNull(updatedCustomer);
        assertEquals(updatedName, updatedCustomer.getFirstName());
        assertThat(originalFirstName, not(equalTo(updatedCustomer.getFirstName())));
        assertThat(originalLastName, equalTo(updatedCustomer.getLastName()));
    }

    @Test
    public void patchCustomerUpdateLastName() throws Exception {
        String updatedName = "updatedName";
        long id = getFirstCustomerId();

        Customer originalCustomer = customerRepository.getOne(id);
        assertNotNull(originalCustomer);
        String originalFirstName = originalCustomer.getFirstName();
        String originalLastName = originalCustomer.getLastName();

        CustomerDto customerDto = CustomerDto.builder().lastName(updatedName).build();

        customerService.patchCustomer(id, customerDto);

        Customer updatedCustomer = customerRepository.findById(id).get();

        assertNotNull(updatedCustomer);
        assertEquals(updatedName, updatedCustomer.getLastName());
        assertThat(originalFirstName, equalTo(updatedCustomer.getFirstName()));
        assertThat(originalLastName, not(equalTo(updatedCustomer.getLastName())));
    }

    // needed due to indices continuing to count after each test
    private Long getFirstCustomerId() {
        List<Customer> customers = customerRepository.findAll();
        System.out.println(customers.size() + " customers found");
        return customers.get(0).getId();
    }
}
