package edu.bu.cs673.secondhand;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication(scanBasePackages = {"edu.bu.cs673.secondhand"})
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}