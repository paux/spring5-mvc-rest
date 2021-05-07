package cc.paukner.bootstrap;

import cc.paukner.domain.Category;
import cc.paukner.domain.Customer;
import cc.paukner.domain.Vendor;
import cc.paukner.repositories.CategoryRepository;
import cc.paukner.repositories.CustomerRepository;
import cc.paukner.repositories.VendorRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Bootstrap implements CommandLineRunner {

    private final CategoryRepository categoryRepository;
    private final CustomerRepository customerRepository;
    private final VendorRepository vendorRepository;

    public Bootstrap(CategoryRepository categoryRepository, CustomerRepository customerRepository, VendorRepository vendorRepository) {
        this.categoryRepository = categoryRepository;
        this.customerRepository = customerRepository;
        this.vendorRepository = vendorRepository;
    }

    @Override
    public void run(String... args) {
        generateCategories();
        generateCustomers();
        generateVendors();
    }

    public void generateCategories() {
        Category fruits = Category.builder().name("Fruits").build();
        Category dried = Category.builder().name("Dried").build();
        Category fresh = Category.builder().name("Fresh").build();
        Category exotic = Category.builder().name("Exotic").build();
        Category nuts = Category.builder().name("Nuts").build();

        categoryRepository.saveAll(List.of(fruits, dried, fresh, exotic, nuts));

        System.out.println("Categories loaded: " + categoryRepository.count());
    }

    public void generateCustomers() {
        Customer seppForcher = Customer.builder().firstName("Sepp").lastName("Forcher").build();
        Customer karlMoik = Customer.builder().firstName("Karl").lastName("Moik").build();

        customerRepository.saveAll(List.of(seppForcher, karlMoik));

        System.out.println("Customers loaded: " + customerRepository.count());
    }

    public void generateVendors() {
        Vendor acme = Vendor.builder().name("ACME, Inc.").build();
        Vendor hinzKunz = Vendor.builder().name("Hinz und Kunz GmbH & Co. KG").build();

        vendorRepository.saveAll(List.of(acme, hinzKunz));

        System.out.println("Vendors loaded: " + vendorRepository.count());
    }
}
