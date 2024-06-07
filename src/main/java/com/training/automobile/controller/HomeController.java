package com.training.automobile.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/auto")
public class HomeController {

    @GetMapping("/")
    public String index() {
        return "auto/index";
    }
}
