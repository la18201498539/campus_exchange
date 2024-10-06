package edu.bu.cs673.secondhand.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/***
 Email: ybinman@bu.edu
 DateTime: 10/6/24-14:03
 *****/
@RestController
public class Index {

    @GetMapping("/")
    public String index() {
        return "Welcome to BU Second hand Trading!";
    }
}
