package edu.bu.cs673.secondhand.service.impl;

import edu.bu.cs673.secondhand.service.EmailService;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.MailException;

import static org.junit.jupiter.api.Assertions.*;

/***
 Email: qyyh@bu.edu
 DateTime: 11/06/24-16:39
 *****/

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class EmailServiceImplTest {

    @Resource
    private EmailService emailService;

    private static final String TEST_EMAIL = "qyyh@bu.edu";
    private static final String TEST_TOKEN = "test-token-123";

    @BeforeEach
    void setUp() {
        System.out.println("Setup Email Service By Spring Boot Annotation [Resource]!");
    }

    @Test
    @Order(1)
    void testSendSimpleMessage() {
        try {
            emailService.sendSimpleMessage(
                TEST_EMAIL,
                "Test Simple Message",
                "This is a test message content"
            );
        } catch (MailException e) {
            fail("Failed to send simple email: " + e.getMessage());
        }
    }

    @Test
    @Order(2)
    void testSendSignUpEmail() {
        try {
            emailService.sendSignUpEmail(TEST_EMAIL, TEST_TOKEN);
        } catch (MailException e) {
            fail("Failed to send sign-up email: " + e.getMessage());
        }
    }

    @Test
    @Order(3)
    void testSendActivationEmail() {
        try {
            emailService.sendActivationEmail(TEST_EMAIL, TEST_TOKEN);
        } catch (MailException e) {
            fail("Failed to send activation email: " + e.getMessage());
        }
    }

    @Test
    @Order(4)
    void testSendPasswordResetEmail() {
        try {
            emailService.sendPasswordResetEmail(TEST_EMAIL, TEST_TOKEN);
        } catch (MailException e) {
            fail("Failed to send password reset email: " + e.getMessage());
        }
    }
}
