package edu.bu.cs673.secondhand.service;

import edu.bu.cs673.secondhand.serviceInterface.EmailServiceInterface;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.MailException;

import static org.junit.jupiter.api.Assertions.fail;

@SpringBootTest
public class EmailServiceInterfaceTest {

    @Autowired
    private EmailServiceInterface emailServiceInterface;  // Injecting the EmailServiceInterface

    /**
     * Test the sending of a simple email message.
     */
    @Test
    public void testSendSimpleMessage() {
        try {
            emailServiceInterface.sendSimpleMessage("qyyh@bu.edu", "Test Subject", "Test Content");  // Attempt to send an email
        } catch (MailException e) {
            e.printStackTrace(); // Print the full stack trace
            fail("Failed to send email: " + e.getMessage() + "\nCause: " + e.getCause());  // Fail the test if an exception occurs
        }
    }
}
