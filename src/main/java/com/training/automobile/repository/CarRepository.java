package com.training.automobile.repository;

import com.training.automobile.entities.Car;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CarRepository extends JpaRepository<Car, Long> {

    List<Car> findByCustomerIsNull();
    List<Car> findAll();

}
