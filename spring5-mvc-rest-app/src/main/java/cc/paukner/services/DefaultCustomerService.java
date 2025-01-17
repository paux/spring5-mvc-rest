package cc.paukner.services;

import cc.paukner.api.v1.mapper.CustomerMapper;
import cc.paukner.api.v1.model.CustomerDto;
import cc.paukner.domain.Customer;
import cc.paukner.repositories.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static cc.paukner.controllers.v1.CustomerController.BASE_URL;

@Service
public class DefaultCustomerService implements CustomerService {

    private final CustomerMapper customerMapper;
    private final CustomerRepository customerRepository;

    public DefaultCustomerService(CustomerMapper customerMapper, CustomerRepository customerRepository) {
        this.customerMapper = customerMapper;
        this.customerRepository = customerRepository;
    }

    @Override
    public List<CustomerDto> getAllCustomers() {
        return customerRepository.findAll()
                .stream()
                .map(customer -> {
                    CustomerDto customerDto = customerMapper.customerToCustomerDto(customer);
                    customerDto.setCustomerUrl(getCustomerUrl(customer.getId()));
                    return customerDto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public CustomerDto getCustomerByName(String firstName, String lastName) {
        return customerMapper.customerToCustomerDto(customerRepository.findByFirstNameAndLastName(firstName, lastName));
    }

    @Override
    public CustomerDto getCustomerById(Long id) {
//        return customerMapper.customerToCustomerDto(customerRepository.findById(id).get());
        return customerRepository.findById(id)
                .map(customerMapper::customerToCustomerDto)
                .orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public CustomerDto createCustomer(CustomerDto customerDto) {
        return saveCustomer(customerMapper.customerDtoToCustomer(customerDto));
    }

    @Override
    public CustomerDto updateCustomer(Long id, CustomerDto customerDto) {
        Customer customer = customerMapper.customerDtoToCustomer(customerDto);
        customer.setId(id);
        return saveCustomer(customer);
    }

    private CustomerDto saveCustomer(Customer customer) {
        CustomerDto savedCustomerAsDto = customerMapper.customerToCustomerDto(customerRepository.save(customer));
        savedCustomerAsDto.setCustomerUrl(getCustomerUrl(savedCustomerAsDto.getId()));
        return savedCustomerAsDto;
    }

    @Override
    public CustomerDto patchCustomer(Long id, CustomerDto customerDto) {
        return customerRepository.findById(id).map(customer -> {
            if (customerDto.getFirstName() != null) {
                customer.setFirstName(customerDto.getFirstName());
            }
            if (customerDto.getLastName() != null) {
                customer.setLastName(customerDto.getLastName());
            }
            CustomerDto returnDto = customerMapper.customerToCustomerDto(customerRepository.save(customer));
            returnDto.setCustomerUrl(getCustomerUrl(id));
            return returnDto;
        }).orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public void deleteCustomerById(Long id) {
        customerRepository.deleteById(id);
    }

    private String getCustomerUrl(Long id) {
        return BASE_URL + id;
    }
}
