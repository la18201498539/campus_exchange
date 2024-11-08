package edu.bu.cs673.secondhand.service.impl;

import edu.bu.cs673.secondhand.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

/**
 * EmailService is responsible for sending emails related to user actions
 * such as sign-up, activation, and password reset.
 * Author: YQ
 * TODO: STILL NEED TO DEVELOP A REGISTER LINK AND RESET PASSWORD LINK
 */
@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender mailSender;  // Mail sender for sending emails

    @Value("${app.url}")
    private String appUrl;  // Base URL of the application

    @Value("${spring.mail.username}")
    private String fromEmail;  // Email address from which the emails will be sent

    /**
     * Sends a simple email message.
     * @param to The recipient's email address.
     * @param subject The subject of the email.
     * @param text The body of the email.
     */
    @Override
    public void sendSimpleMessage(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromEmail);  // Set the sender's email
        message.setTo(to);  // Set the recipient's email
        message.setSubject(subject);  // Set the subject
        message.setText(text);  // Set the body text
        mailSender.send(message);  // Send the email
    }

    /**
     * Sends a sign-up email to the user with a link to complete the sign-up process.
     * @param to The recipient's email address.
     * @param token The activation token for the sign-up process.
     */
    @Override
    public void sendSignUpEmail(String to, String token) {
        String subject = "Complete Your Sign-Up for SecondHand";
        String text = "Please click on the following link to complete your sign-up: "
                + appUrl + "/api/users/complete-signup?email=" + to + "&token=" + token
                + "\n\nThis link will expire in 24 hours.";
        sendSimpleMessage(to, subject, text);  // Send the sign-up email
    }

    /**
     * Sends an activation email to the user.
     * @param email The recipient's email address.
     * @param activationToken The activation token for the user.
     */
    @Override
    public void sendActivationEmail(String email, String activationToken) {
        // Implement this method if needed
        String subject = "Activate Your Account for SecondHand";
        String text = "Please click on the following link to activate your account: "
                + appUrl + "/api/users/activate?email=" + email + "&token=" + activationToken
                + "\n\nThis link will expire in 24 hours.";
        sendSimpleMessage(email, subject, text);  // Send the activation email
    }

    /**
     * Sends a password reset email to the user with a link to reset their password.
     * @param email The recipient's email address.
     * @param resetToken The token for resetting the password.
     */
    @Override
    public void sendPasswordResetEmail(String email, String resetToken) {
        String subject = "Reset Your Password for SecondHand";
        String text = "Please click on the following link to reset your password: "
                + appUrl + "/api/users/reset-password?email=" + email + "&resetToken=" + resetToken
                + "\n\nThis link will expire in 1 hour.";
        sendSimpleMessage(email, subject, text);  // Send the password reset email
    }
}