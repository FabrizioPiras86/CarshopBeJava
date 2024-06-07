package com.training.automobile.controller;

import com.training.automobile.controller.business.CustomerService;
import com.training.automobile.entities.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;


    @GetMapping("/")
    public String index(Model model) {
        List<Customer> customers = customerService.getAllCustomersOrderedByLastName();
        model.addAttribute("customers", customers);
        return "customer/index";
    }


    @GetMapping("/create")
    public String showCustomerForm(Model model) {
        model.addAttribute("customer", new Customer());
        return "customer/create";
    }


    @PostMapping("/create")
    public String createCustomer(@ModelAttribute("customer") Customer customer) {
        customerService.saveCustomer(customer);
        return "redirect:/customer/";
    }


    @PostMapping("/delete/{id}")
    public String deleteCustomer(@PathVariable Long id) {
        customerService.deleteCustomer(id);
        return "redirect:/customer/";
    }


    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable Long id, Model model) {
        Optional<Customer> customer = customerService.findCustomerById(id);
        if (customer.isPresent()) {
            // Formattiamo la data nel formato corretto "yyyy-MM-dd" prima di passarla al modello
            Customer customerObj = customer.get();
            if (customerObj.getDob() != null) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                String formattedDob = customerObj.getDob().format(formatter);
                model.addAttribute("dob", formattedDob);
            }
            model.addAttribute("customer", customerObj);
        } else {
            model.addAttribute("error", "Cliente non trovato");
        }
        return "customer/update";
    }



    @PostMapping("/update/{id}")
    public String updateCustomer(@PathVariable Long id, @ModelAttribute("customer") Customer customer) {
        customer.setId(id);
        customerService.updateCustomer(customer);
        return "redirect:/customer/";
    }
}
