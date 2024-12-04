package edu.bu.cs673.secondhand;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

/***
 Email: ybinman@bu.edu
 DateTime: 10/27/24-00:50
 *****/
@SpringBootTest()
@TestPropertySource("/application-test.properties")
public class ApplicationTest {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
