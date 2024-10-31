package edu.bu.cs673.secondhand;

import org.springframework.boot.SpringApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Main class to start the Spring Boot application.
 * It also defines the main entry point and a simple welcome endpoint.
 * Author: YQ
 */
//@SpringBootApplication
@MapperScan("edu.bu.cs673.secondhand.mapper")  // Scan for MyBatis mappers
@RestController
public class Main {

    private static final Logger logger = LoggerFactory.getLogger(Main.class);  // Logger for the application

//    public static void main(String[] args) {
//        SpringApplication.run(Main.class, args);  // Start the Spring Boot application
//        logger.info("Application started successfully!");  // Log application start
//    }

    // Remove this method if it's not needed
    // @GetMapping("/")
    // public String index() {
    //     return "Welcome to BU Second hand Trading!";  // Return welcome message
    // }
}
