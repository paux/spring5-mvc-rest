package cc.paukner.controllers;

import cc.paukner.api.v1.model.CustomerDto;
import cc.paukner.api.v1.model.CustomerListDto;
import cc.paukner.services.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/v1/customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public ResponseEntity<CustomerListDto> getAllCustomers() {
        return new ResponseEntity<>(new CustomerListDto(customerService.getAllCustomers()), HttpStatus.OK);
    }

    @GetMapping("/{lastName}/{firstName}")
    public ResponseEntity<CustomerDto> getCustomerByName(@PathVariable String lastName, @PathVariable String firstName) {
        return new ResponseEntity<>(customerService.getCustomerByName(firstName, lastName), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerDto> getCustomerById(@PathVariable Long id) {
        return new ResponseEntity<>(customerService.getCustomerById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CustomerDto> createCustomer(@RequestBody CustomerDto customerDto) {
        return new ResponseEntity<>(customerService.createCustomer(customerDto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomerDto> updateCustomer(@PathVariable Long id, @RequestBody CustomerDto customerDto) {
        return new ResponseEntity<>(customerService.updateCustomer(id, customerDto), HttpStatus.OK);
    }
}
