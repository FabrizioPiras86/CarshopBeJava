package com.training.automobile.controller.business;

import com.training.automobile.entities.Car;
import com.training.automobile.entities.Customer;
import com.training.automobile.repository.CarRepository;
import com.training.automobile.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CarService {

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private CustomerRepository customerRepository;

    public List<Car> getAvailableCars() {
        return carRepository.findByCustomerIsNull();
    }

    public List<Car> getCars() {
        return carRepository.findAll();
    }

    public Optional<Customer> findCustomerById(Long id) {
        return customerRepository.findById(id);
    }

    public Car updateCar(Long id, Car updatedCar) {
        Optional<Car> carOptional = carRepository.findById(id);
        if (carOptional.isPresent()) {
            Car existingCar = carOptional.get();
            existingCar.setBrand(updatedCar.getBrand());
            existingCar.setName(updatedCar.getName());
            existingCar.setPlate(updatedCar.getPlate());
            existingCar.setCustomer(updatedCar.getCustomer());
            return carRepository.save(existingCar);
        }
        return null;
    }

    public Car save(Car car) {
        return carRepository.save(car);
    }

    public Car getById(Long id) {
        Optional<Car> carOptional = carRepository.findById(id);
        return carOptional.orElse(null);
    }

    public void deleteCar(Long id) {
        carRepository.deleteById(id);
    }
}
