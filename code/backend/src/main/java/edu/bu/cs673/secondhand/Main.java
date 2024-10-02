package edu.bu.cs673.secondhand;

import org.springframework.boot.SpringApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;
/***
 Name:  Yuanbin Man, ex-IBM, ex-Alibaba, AI Engineer in CV/DL/MLSys
 Email: ybinman@bu.edu
 DateTime: ${DATE}-${TIME}
 Publications:  https://arxiv.org/abs/2312.16385; https://ieeexplore.ieee.org/document/10379821;
 *****/

@SpringBootApplication
@RestController
public class Main {

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }


    @GetMapping("/")
    public String index() {
        return "Welcome to BU Second hand Trading!";
    }
}