package edu.bu.cs673.secondhand.controller;

import edu.bu.cs673.secondhand.model.UserModel;
import edu.bu.cs673.secondhand.vo.ResultVo;
import edu.bu.cs673.secondhand.serviceInterface.UserServiceInterface;
import org.antlr.v4.runtime.misc.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import edu.bu.cs673.secondhand.utils.JwtUtil;

/**
 * UserController handles user-related operations such as sign-up, login, password reset, and logout.
 * Author: YQ
 */
@RestController
@RequestMapping("/user")  // Change the base path for user-related endpoints
public class UserController {

    @Autowired
    private UserServiceInterface userServiceInterface;

    @Autowired
    private JwtUtil jwtUtil;

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    /**
     * Initiates the sign-up process for a user.
     * @param request The request containing the user's email.
     * @return ResultVo indicating success or failure.
     */
    @PostMapping("/initiate-signup")
    public ResultVo initiateSignUp(@RequestBody InitiateSignUpRequest request) {
        if (request == null || request.getEmail() == null) {
            return ResultVo.fail("Email is required.");
        }

        if (!request.getEmail().toLowerCase().endsWith("@bu.edu")) {
            return ResultVo.fail("Only BU email addresses are allowed.");
        }

        try {
            userServiceInterface.initiateSignUp(request.getEmail());
            return ResultVo.success("Sign-up initiated. Please check your email to complete the registration.");
        } catch (IllegalArgumentException e) {
            logger.warn("Invalid sign-up attempt: {}", e.getMessage());
            return ResultVo.fail(e.getMessage());
        } catch (RuntimeException e) {
            logger.error("Error during sign-up initiation", e);
            return ResultVo.fail("An unexpected error occurred. Please try again later.");
        }
    }

    /**
     * Completes the sign-up process for a user.
     * @param request The request containing the user's email, token, password, and nickname.
     * @return ResultVo indicating success or failure.
     */
    @PostMapping("/complete-signup")
    public ResultVo completeSignUp(@RequestBody CompleteSignUpRequest request) {
        if (request == null || request.getEmail() == null || request.getToken() == null
                || request.getPassword() == null || request.getNickname() == null) {
            return ResultVo.fail("All fields are required.");
        }

        try {
            userServiceInterface.completeSignUp(request.getEmail(), request.getToken(),
                    request.getPassword(), request.getNickname());
            return ResultVo.success("Registration completed successfully. You can now log in.");
        } catch (IllegalArgumentException e) {
            logger.warn("Invalid sign-up completion attempt: {}", e.getMessage());
            return ResultVo.fail(e.getMessage());
        } catch (RuntimeException e) {
            logger.error("Error during sign-up completion", e);
            return ResultVo.fail("An unexpected error occurred. Please try again later.");
        }
    }

    /**
     * Logs in a user and generates a JWT token.
     * @param request The request containing the user's email and password.
     * @return ResultVo indicating success or failure.
     */
    @PostMapping("/login")
    public ResultVo login(@RequestBody LoginRequest request) {
        if (request == null || request.getEmail() == null || request.getPassword() == null) {
            return ResultVo.fail("Email and password are required.");
        }

        try {
            UserModel user = userServiceInterface.login(request.getEmail(), request.getPassword());
            if (user != null) {
                String token = jwtUtil.generateToken(user.getEmail());  // Generate token
                return ResultVo.success("Login successful", new LoginResponse(token, user));
            } else {
                return ResultVo.fail("Invalid email or password.");
            }
        } catch (Exception e) {
            logger.error("Error during login", e);
            return ResultVo.fail("An unexpected error occurred. Please try again later.");
        }
    }

    /**
     * Logs out a user by invalidating the provided token.
     * @param token The authorization token.
     * @return ResponseEntity indicating success or failure.
     */
    @PostMapping("/logout")
    public ResponseEntity<ResultVo> logout(@RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
            try {
                // Here you can add logic to blacklist the token
                // userServiceInterface.addTokenToBlacklist(token);

                return ResponseEntity.ok(ResultVo.success("Logged out successfully"));
            } catch (Exception e) {
                logger.error("Error during logout", e);
                return ResponseEntity.badRequest().body(ResultVo.fail("An error occurred during logout"));
            }
        }
        return ResponseEntity.badRequest().body(ResultVo.fail("Invalid token"));
    }

    /**
     * Initiates the password reset process for a user.
     * @param email The user's email.
     * @return ResultVo indicating success or failure.
     * TODO: STILL NEED TO MODIFY
     */
    @PostMapping("/request-reset-password")
    public ResultVo requestResetPassword(@RequestBody String email) {
        if (email == null || email.isEmpty()) {
            return ResultVo.fail("Email is required.");
        }

        boolean result = userServiceInterface.initiatePasswordReset(email);
        if (result) {
            return ResultVo.success("Password reset email sent.");
        } else {
            return ResultVo.fail("User not found.");
        }
    }

    /**
     * Completes the password reset process for a user.
     * @param request The request containing the user's email, reset token, and new password.
     * @return ResultVo indicating success or failure.
     */
    @PostMapping("/complete-reset")
    public ResultVo completeReset(@RequestBody ResetPasswordRequest request) {
        if (request == null || request.getEmail() == null || request.getResetToken() == null || request.getNewPassword() == null) {
            return ResultVo.fail("Email, reset token, and new password are required.");
        }

        try {
            boolean result = userServiceInterface.resetPassword(request.getEmail(), request.getResetToken(), request.getNewPassword());
            if (result) {
                return ResultVo.success("Password reset successfully.");
            } else {
                return ResultVo.fail("Invalid email or reset token.");
            }
        } catch (Exception e) {
            logger.error("Error during password reset", e);
            return ResultVo.fail("An unexpected error occurred. Please try again later.");
        }
    }

    // Inner classes for request bodies
    private static class InitiateSignUpRequest {
        private String email;

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }
    }

    private static class CompleteSignUpRequest {
        private String email;
        private String token;
        private String password;
        private String nickname;

        // Getters and setters
        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }
    }

    private static class LoginRequest {
        private String email;
        private String password;

        // Getters and setters
        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }

    // Inner class for login response
    private static class LoginResponse {
        private String token;
        private UserModel user;

        public LoginResponse(String token, UserModel user) {
            this.token = token;
            this.user = user;
        }

        // Ensure there are getter methods
        public String getToken() {
            return token;
        }

        public UserModel getUser() {
            return user;
        }
    }

    private static class ResetPasswordRequest {
        private String email;
        private String resetToken;
        private String newPassword;

        // Getters and setters
        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getResetToken() {
            return resetToken;
        }

        public void setResetToken(String resetToken) {
            this.resetToken = resetToken;
        }

        public String getNewPassword() {
            return newPassword;
        }

        public void setNewPassword(String newPassword) {
            this.newPassword = newPassword;
        }
    }
}