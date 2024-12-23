package itk.academy.orekhov.ordermanagementsystem.service;

import itk.academy.orekhov.ordermanagementsystem.model.Customer;
import itk.academy.orekhov.ordermanagementsystem.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    public Customer createCustomer(Customer customer) {
        return customerRepository.save(customer);
    }
}
