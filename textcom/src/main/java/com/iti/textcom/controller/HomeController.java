package com.iti.textcom.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class HomeController {
    @GetMapping("/")
    public String home() {
        return "index"; 
    }

    @GetMapping("/Aboutus")
    public String aboutus() {
        return "Aboutus";
    }
}
