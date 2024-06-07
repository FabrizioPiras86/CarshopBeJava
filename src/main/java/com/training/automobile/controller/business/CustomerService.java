package com.training.automobile.controller.business;

import com.training.automobile.entities.Customer;
import com.training.automobile.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;


    public List<Customer> getAllCustomersOrderedByLastName() {
        return customerRepository.findAllByOrderByLnameAsc();
    }


    public Optional<Customer> findCustomerById(Long id) {
        return customerRepository.findById(id);
    }


    public void saveCustomer(Customer customer) {
        customerRepository.save(customer);
    }


    public void deleteCustomer(Long id) {
        customerRepository.deleteById(id);
    }


    public void updateCustomer(Customer customer) {
        // Formattiamo la data nel formato corretto "yyyy-MM-dd"
        LocalDate dob = customer.getDob();
        if (dob != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String formattedDob = dob.format(formatter);
            customer.setDob(LocalDate.parse(formattedDob, formatter));
        }
        customerRepository.save(customer);
    }
}
