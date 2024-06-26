package com.training.automobile.entities;


import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.antlr.v4.runtime.misc.NotNull;

import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "customer")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Size(max = 100)
    @Column(name = "fname", length = 100)
    private String fname;

    @Size(max = 100)
    @NotNull
    @Column(name = "lname", nullable = false, length = 100)
    private String lname;

    @Column(name = "dob")
    private LocalDate dob;

    @Size(max = 30)
    @Column(name = "phone", length = 30)
    private String phone;

    @OneToMany(mappedBy = "customer")
    private Set<Car> cars = new LinkedHashSet<>();

}