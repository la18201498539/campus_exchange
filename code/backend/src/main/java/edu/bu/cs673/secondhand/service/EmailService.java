package edu.bu.cs673.secondhand.service;

/**
 * EmailServiceInterface defines the methods for sending various types of emails
 * related to user actions such as sign-up, activation, and password reset.
 * Author: YQ
 */
public interface EmailService {
    /**
     * Sends a simple email message.
     * @param to The recipient's email address.
     * @param subject The subject of the email.
     * @param text The body of the email.
     */
    void sendSimpleMessage(String to, String subject, String text);

    /**
     * Sends a sign-up email to the user with a link to complete the sign-up process.
     * @param to The recipient's email address.
     * @param token The activation token for the sign-up process.
     */
    void sendSignUpEmail(String to, String token);

    /**
     * Sends an activation email to the user.
     * @param email The recipient's email address.
     * @param activationToken The activation token for the user.
     */
    void sendActivationEmail(String email, String activationToken);

    /**
     * Sends a password reset email to the user with a link to reset their password.
     * @param email The recipient's email address.
     * @param resetToken The token for resetting the password.
     */
    void sendPasswordResetEmail(String email, String resetToken);
}
