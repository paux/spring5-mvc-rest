package cc.paukner.services;

import cc.paukner.api.v1.model.CustomerDto;

import java.util.List;

public interface CustomerService {

    List<CustomerDto> getAllCustomers();

    CustomerDto getCustomerByName(String firstName, String lastName);

    CustomerDto getCustomerById(Long id);

    CustomerDto createCustomer(CustomerDto customerDto);

    CustomerDto updateCustomer(Long id, CustomerDto customerDto);
}
