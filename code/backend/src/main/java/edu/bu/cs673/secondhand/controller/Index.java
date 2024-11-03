package edu.bu.cs673.secondhand.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/***
 Email: qyyh@bu.edu,la1993@bu.edu
 DateTime: 11/3/24-14:03
 *****/
@RestController
public class Index {

    @GetMapping("/about")
    public String index() {
        return "Welcome to BU Second hand Trading!";
    }
}
