package com.training.automobile.controller;

import com.training.automobile.controller.business.CarService;
import com.training.automobile.controller.business.CustomerService;
import com.training.automobile.entities.Car;
import com.training.automobile.entities.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/car")
public class CarController {

    @Autowired
    private CarService carService;

    @Autowired
    private CustomerService customerService;

    @GetMapping("/")
    public String index(Model model) {
        List<Car> cars = carService.getCars();
        model.addAttribute("cars", cars);
        return "car/index";
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("car", new Car());
        List<Customer> customers = customerService.getAllCustomersOrderedByLastName();
        model.addAttribute("customers", customers);
        return "car/create"; // Template name
    }

    @PostMapping("/create")
    public String insert(@ModelAttribute Car car, Model model) {
        if (car.getCustomer() != null && car.getCustomer().getId() != null) {
            Optional<Customer> customerOpt = customerService.findCustomerById(car.getCustomer().getId());
            customerOpt.ifPresent(car::setCustomer);
        }
        carService.save(car);
        List<Car> cars = carService.getCars(); //
        model.addAttribute("cars", cars); //
        return "redirect:/car/"; //
    }

    @GetMapping("/update/{id}")
    public String updateCar(@PathVariable Long id, Model model) {
        Car car = carService.getById(id);
        if (car != null) {
            model.addAttribute("car", car);
            List<Customer> customers = customerService.getAllCustomersOrderedByLastName();
            model.addAttribute("customers", customers);
            return "car/update"; // Template name
        } else {
            return "car/not_found"; // Template name
        }
    }


    @PostMapping("/update/{id}")
    public String updateCar(@PathVariable Long id, @ModelAttribute("car") Car updatedCar, @RequestParam(required = false) boolean removeCustomer) {
        Car existingCar = carService.getById(id);
        if (existingCar != null) {
            if (removeCustomer) {
                existingCar.setCustomer(null);
            } else {
                if (updatedCar.getCustomer() != null && updatedCar.getCustomer().getId() != null) {
                    Optional<Customer> customerOpt = customerService.findCustomerById(updatedCar.getCustomer().getId());
                    customerOpt.ifPresent(existingCar::setCustomer);
                }
            }
            existingCar.setBrand(updatedCar.getBrand());
            existingCar.setName(updatedCar.getName());
            existingCar.setPlate(updatedCar.getPlate());
            carService.save(existingCar);
            return "redirect:/car/";
        } else {
            return "car_not_found";
        }

    }
    @PostMapping("/delete/{id}")
    public String deleteCar(@PathVariable Long id) {
        carService.deleteCar(id);
        return "redirect:/car/";
    }

}
