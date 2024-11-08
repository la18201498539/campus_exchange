package edu.bu.cs673.secondhand.controller;

import edu.bu.cs673.secondhand.model.UserModel;
import edu.bu.cs673.secondhand.service.UserService;
import edu.bu.cs673.secondhand.vo.ResultVo;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import edu.bu.cs673.secondhand.utils.JwtUtil;

import javax.validation.constraints.NotNull;
import java.util.UUID;

/***
 Email: qyyh@bu.edu,la1993@bu.edu
 DateTime: 11/3/24-14:03
 *****/
//@RestController
//@RequestMapping("/user")  // Change the base path for user-related endpoints
public class UserController {

    @Autowired
    private UserService userServiceInterface;

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
        logger.info("Received login request");
        logger.debug("Raw request body: {}", request);
        if (request == null || request.getEmail() == null || request.getPassword() == null) {
            return ResultVo.fail("Email and password are required.");
        }

        try {
            UserModel user = userServiceInterface.login(request.getEmail(), request.getPassword());
            logger.info("User logged in successfully: {}", user.getEmail());
            String token = jwtUtil.generateToken(user.getEmail());
            return ResultVo.success("Login successful", new LoginResponse(token, user));
        } catch (RuntimeException e) {
            logger.warn("Login failed: {}", e.getMessage());
            return ResultVo.fail(e.getMessage());
        }
    }

    /**
     * Logs out a user by invalidating the provided token.
     * @param token The authorization token.
     * @return ResponseEntity indicating success or failure.
     */
    @PostMapping("/logout")
    public ResponseEntity<ResultVo> logout(@RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        // TODO
        // Release token when logout. @Yihan
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

    /**
     * Registers a new user and sends an activation email.
     * @param request The request containing the user's username, email, password, and confirm password.
     * @return ResultVo indicating success or failure.
     */
    @PostMapping("/register")
    public ResultVo register(@RequestBody RegisterRequest request) {
        if (request == null || request.getUsername() == null || request.getEmail() == null ||
            request.getPassword() == null || request.getConfirmPassword() == null) {
            return ResultVo.fail("All fields are required.");
        }

        if (!request.getPassword().equals(request.getConfirmPassword())) {
            return ResultVo.fail("Passwords do not match.");
        }

        if (!request.getEmail().toLowerCase().endsWith("@bu.edu")) {
            return ResultVo.fail("Only BU email addresses are allowed.");
        }

        try {
            // Generate activation token
            String activationToken = UUID.randomUUID().toString();
            userServiceInterface.registerUser(request.getUsername(), request.getEmail(), request.getPassword(), activationToken);
            // Send activation email
            userServiceInterface.sendActivationEmail(request.getEmail(), activationToken);
            return ResultVo.success("Registration successful. Please check your email to activate your account.");
        } catch (IllegalArgumentException e) {
            logger.warn("Invalid registration attempt: {}", e.getMessage());
            return ResultVo.fail(e.getMessage());
        } catch (RuntimeException e) {
            logger.error("Error during registration", e);
            return ResultVo.fail("An unexpected error occurred. Please try again later.");
        }
    }

    /**
     * Activates a user account using the provided email and activation token.
     * @param email The user's email.
     * @param token The activation token.
     * @return ResponseEntity indicating success or failure.
     */
    @GetMapping("/activate")
    public ResponseEntity<?> activateAccount(@RequestParam(required = false) String email,
                                         @RequestParam(required = false) String token) {
        if (email == null || email.isEmpty()) {
            logger.warn("Activation request received with missing email");
            return ResponseEntity.badRequest().body(ResultVo.fail("Email is required"));
        }
        if (token == null || token.isEmpty()) {
            logger.warn("Activation request received with missing token for email: {}", email);
            return ResponseEntity.badRequest().body(ResultVo.fail("Activation token is required"));
        }

        logger.info("Received activation request for email: {}", email);
        try {
            boolean activated = userServiceInterface.activateUser(email, token);
            if (activated) {
                logger.info("Account activated successfully for email: {}", email);
                return ResponseEntity.ok(ResultVo.success("Account activated successfully. You can now log in."));
            } else {
                logger.warn("Account activation failed for email: {}", email);
                return ResponseEntity.badRequest().body(ResultVo.fail("Invalid activation link or account already activated."));
            }
        } catch (Exception e) {
            logger.error("Error during account activation for email: " + email, e);
            return ResponseEntity.internalServerError().body(ResultVo.fail("An error occurred during account activation."));
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

        // 确保有一个无参构造函数
        public LoginRequest() {}

        // Getters and setters
        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }

        @Override
        public String toString() {
            return "LoginRequest{email='" + email + "', password='[PROTECTED]'}";
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

    // Inner class for registration request
    private static class RegisterRequest {
        private String username;
        private String email;
        private String password;
        private String confirmPassword;

        // Getters and setters
        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

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

        public String getConfirmPassword() {
            return confirmPassword;
        }

        public void setConfirmPassword(String confirmPassword) {
            this.confirmPassword = confirmPassword;
        }
    }

    private String generateAccountNumber() {
        // 生成一个随机的账号，确保它符合 varchar(16) 的限制
        return UUID.randomUUID().toString().substring(0, 16); // 示例：使用 UUID
    }
}
