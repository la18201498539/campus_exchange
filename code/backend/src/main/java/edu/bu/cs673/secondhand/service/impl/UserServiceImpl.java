package edu.bu.cs673.secondhand.service.impl;

import edu.bu.cs673.secondhand.mapper.UserMapper;
import edu.bu.cs673.secondhand.model.UserModel;
import edu.bu.cs673.secondhand.service.EmailService;
import edu.bu.cs673.secondhand.service.UserService;
import edu.bu.cs673.secondhand.utils.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Calendar;
import java.util.UUID;
import java.util.Date;
import edu.bu.cs673.secondhand.domain.User;

import com.google.common.collect.Sets;
import java.util.Set;
import java.util.Random;

//
// Email: qyyh@bu.edu,la1993@bu.edu
// DateTime: 11/3/24-14:03
//
//@Service
public class UserServiceImpl implements UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserMapper userMapper;  // Mapper for user database operations
    private final SecurityUtil securityUtil;  // Utility for security-related operations
    private final EmailService emailServiceInterface;  // Service for sending emails
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserMapper userMapper, SecurityUtil securityUtil, EmailService emailServiceInterface, PasswordEncoder passwordEncoder) {
        this.userMapper = userMapper;
        this.securityUtil = securityUtil;
        this.emailServiceInterface = emailServiceInterface;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Registers a new user in the system.
     * @param userModel The user model containing user information.
     * @return true if registration is successful, false otherwise.
     */
    @Override
    public boolean registerUser(UserModel userModel) {
        if (!isBUEmail(userModel.getEmail())) {
            return false;  // Only BU email addresses are allowed
        }

        User existingUser = userMapper.findByEmail(userModel.getEmail());
        if (existingUser != null) {
            throw new RuntimeException("Email already exists");  // Email already registered
        }

        String activationToken = UUID.randomUUID().toString();  // Generate activation token

        userModel.setActivationToken(activationToken);
        userModel.setUserPassword(securityUtil.encodePassword(userModel.getUserPassword()));  // Encrypt password
        userModel.setCreatedAt(new Date());
        userModel.setUpdatedAt(new Date());
        userModel.setActive(false);
        userModel.setUserStatus((byte) 0);

        User user = convertToUser(userModel);  // Convert UserModel to User

        int result = userMapper.insertSelective(user);  // Insert user into the database

        if (result > 0) {
            emailServiceInterface.sendActivationEmail(userModel.getEmail(), activationToken);  // Send activation email
            return true;
        }

        return false;  // Registration failed
    }

    /**
     * Activates a user account using the provided email and activation token.
     * @param email The user's email address.
     * @param activationToken The activation token.
     * @return true if activation is successful, false otherwise.
     */
    @Override
    public boolean activateUser(String email, String activationToken) {
        User user = userMapper.findByEmailAndToken(email, activationToken);
        if (user == null) {
            return false;  // User not found
        }

        if (isTokenExpired(user.getCreatedAt())) {
            return false;  // Token has expired
        }

        user.setActive(true);  // Activate user account
        user.setUserStatus((byte) 1);  // Set user status to active
        user.setActivationToken(null);  // Clear activation token
        user.setUpdatedAt(new Date());

        int result = userMapper.updateByPrimaryKey(user);  // Update user in the database
        return result > 0;  // Return true if update was successful
    }

    /**
     * Logs in a user by verifying their email and password.
     * @param email The user's email address.
     * @param password The user's password.
     * @return UserModel if login is successful, null otherwise.
     */
    @Override
    public UserModel login(String email, String password) {
        User user = userMapper.findByEmail(email);
        if (user == null) {
            throw new RuntimeException("Email does not exist.");
        }

        if (!user.isActive()) {
            throw new RuntimeException("Account is not activated. Please check your email for the activation link.");
        }

        if (!securityUtil.matchPassword(password, user.getUserPassword())) {
            throw new RuntimeException("Incorrect password.");
        }

        return convertToUserModel(user);
    }

    /**
     * Initiates the password reset process by generating a reset token.
     * @param email The user's email address.
     * @return true if the process is initiated successfully, false otherwise.
     */
    @Override
    public boolean initiatePasswordReset(String email) {
        User user = userMapper.findByEmail(email);
        if (user == null) {
            return false;  // User does not exist
        }

        String resetToken = UUID.randomUUID().toString();  // Generate reset token
        user.setActivationToken(resetToken);  // Store reset token in user record
        user.setTokenExpiry(calculateTokenExpiry());  // Set token expiry time
        userMapper.updateByPrimaryKey(user);  // Update user information

        // Send password reset email
        emailServiceInterface.sendPasswordResetEmail(email, resetToken);
        return true;  // Password reset initiated
    }

    /**
     * Resets the user's password using the provided reset token.
     * @param email The user's email address.
     * @param resetToken The reset token.
     * @param newPassword The new password.
     * @return true if the password is reset successfully, false otherwise.
     */
    @Override
    public boolean resetPassword(String email, String resetToken, String newPassword) {
        User user = userMapper.findByEmailAndToken(email, resetToken);
        if (user == null || isTokenExpired(user.getTokenExpiry())) {
            return false;  // Invalid user or token expired
        }
        user.setUserPassword(securityUtil.encodePassword(newPassword));  // Encrypt new password
        user.setActivationToken(null);  // Clear activation token
        user.setTokenExpiry(null);  // Clear token expiry
        user.setUpdatedAt(new Date());
        return userMapper.updateByPrimaryKey(user) > 0;  // Update user in the database
    }

    /**
     * Initiates the sign-up process for a user.
     * @param email The user's email address.
     */
    @Override
    public void initiateSignUp(String email) {
        if (!isBUEmail(email)) {
            throw new IllegalArgumentException("Only BU email addresses are allowed.");  // Validate email
        }

        User existingUser = userMapper.findByEmail(email);
        if (existingUser != null) {
            throw new IllegalArgumentException("Email already exists");  // Email already registered
        }

        User newUser = new User();
        newUser.setEmail(email);  // Set email field
        newUser.setAccountNumber(generateAccountNumber());  // Generate account number
        newUser.setActivationToken(generateActivationToken());  // Generate activation token
        newUser.setTokenExpiry(calculateTokenExpiry());  // Set token expiry time
        newUser.setActive(false);  // Set account as inactive
        newUser.setCreatedAt(new Date());
        newUser.setUpdatedAt(new Date());

        // Set a temporary password
        newUser.setUserPassword("tempPassword");  // Use a temporary password, user will change it later

        // Set a default nickname
        newUser.setNickname("User" + generateAccountNumber());

        // Set a default avatar
        newUser.setAvatar("default_avatar.png");

        try {
            userMapper.insertSelective(newUser);  // Insert new user into the database
            emailServiceInterface.sendActivationEmail(email, newUser.getActivationToken());  // Send activation email
        } catch (Exception e) {
            logger.error("Error inserting user: " + email, e);
            throw new RuntimeException("Failed to create user account.");  // Handle insertion error
        }
    }

    /**
     * Completes the sign-up process for a user.
     * @param email The user's email address.
     * @param token The activation token.
     * @param password The user's password.
     * @param nickname The user's nickname.
     */
    @Override
    public void completeSignUp(String email, String token, String password, String nickname) {
        User user = userMapper.findByEmailAndToken(email, token);
        if (user == null) {
            logger.warn("No user found for email: {} and token: {}", email, token);
            throw new RuntimeException("Invalid sign-up link");  // Invalid link
        }

        logger.info("User found. User ID: {}, Is active: {}, Token expiry: {}", user.getId(), user.isActive(), user.getTokenExpiry());

        Boolean isActive = user.isActive();
        if (isActive != null && isActive) {
            logger.warn("User is already active: {}", email);
            throw new RuntimeException("User is already active");  // User already activated
        }

        if (isTokenExpired(user.getTokenExpiry())) {
            logger.warn("Token expired for user: {}. Expiry: {}", email, user.getTokenExpiry());
            throw new RuntimeException("Sign-up link has expired");  // Token expired
        }

        user.setUserPassword(securityUtil.encodePassword(password));  // Encrypt password
        user.setNickname(nickname);  // Set nickname
        user.setActive(true);  // Activate user account
        user.setUserStatus((byte) 1);  // Set user status to active
        user.setActivationToken(null);  // Clear activation token
        user.setTokenExpiry(null);  // Clear token expiry
        user.setUpdatedAt(new Date());

        logger.info("Updating user information for email: {}", email);
        int updatedRows = userMapper.updateByPrimaryKey(user);  // Update user in the database
        if (updatedRows > 0) {
            logger.info("User sign-up completed successfully for email: {}", email);
        } else {
            logger.error("Failed to update user in database for email: {}", email);
            throw new RuntimeException("Failed to complete sign-up");  // Update failed
        }
    }

    /**
     * Retrieves a user by their email address.
     * @param email The user's email address.
     * @return UserModel if found, null otherwise.
     */
    @Override
    public UserModel getUserByEmail(String email) {
        User user = userMapper.findByEmail(email);
        return user != null ? convertToUserModel(user) : null;  // Convert User to UserModel
    }

    /**
     * Updates user information.
     * @param userModel The user model containing updated information.
     * @return true if the update is successful, false otherwise.
     */
    @Override
    public boolean updateUser(UserModel userModel) {
        User user = convertToUser(userModel);  // Convert UserModel to User
        user.setUpdatedAt(new Date());
        return userMapper.updateByPrimaryKey(user) > 0;  // Update user in the database
    }

    /**
     * Updates the user's avatar.
     * @param email The user's email address.
     * @param avatarUrl The new avatar URL.
     * @return true if the update is successful, false otherwise.
     */
    @Override
    public boolean updateAvatar(String email, String avatarUrl) {
        User user = userMapper.findByEmail(email);
        if (user == null) {
            return false;  // User does not exist
        }
        user.setAvatar(avatarUrl);  // Update avatar URL
        user.setUpdatedAt(new Date());
        return userMapper.updateByPrimaryKey(user) > 0;  // Update user in the database
    }

    /**
     * Retrieves the user's account number.
     * @param email The user's email address.
     * @return The account number if found, null otherwise.
     */
    @Override
    public String getAccountNumber(String email) {
        User user = userMapper.findByEmail(email);
        return user != null ? user.getAccountNumber() : null;  // Return account number
    }

    /**
     * Checks if the provided token has expired.
     * @param email The user's email address.
     * @param token The token to check.
     * @return true if the token has expired, false otherwise.
     */
    @Override
    public boolean isTokenExpired(String email, String token) {
        User user = userMapper.findByEmailAndToken(email, token);
        return user == null || isTokenExpired(user.getTokenExpiry());  // Check token expiry
    }

    private Date calculateTokenExpiry() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR, 1);  // Set token validity to 1 hour
        return calendar.getTime();
    }

    private String generateActivationToken() {
        return UUID.randomUUID().toString();  // Generate a random activation token
    }

    private boolean isTokenExpired(Date tokenExpiry) {
        if (tokenExpiry == null) {
            return true;  // Token is expired if no expiry date is set
        }
        return new Date().after(tokenExpiry);  // Check if current date is after token expiry
    }

    private boolean isBUEmail(String email) {
        return email != null && email.toLowerCase().endsWith("@bu.edu");  // Check if email is a BU email
    }

    private User convertToUser(UserModel userModel) {
        User user = new User();
        user.setId(userModel.getId());
        user.setEmail(userModel.getEmail());
        user.setUserPassword(userModel.getUserPassword());
        user.setNickname(userModel.getNickname());
        user.setAvatar(userModel.getAvatar());
        user.setSignInTime(userModel.getSignInTime());
        user.setUserStatus(userModel.getUserStatus());
        user.setActive(userModel.isActive());
        user.setActivationToken(userModel.getActivationToken());
        user.setCreatedAt(userModel.getCreatedAt());
        user.setUpdatedAt(userModel.getUpdatedAt());
        return user;  // Convert UserModel to User
    }

    private UserModel convertToUserModel(User user) {
        UserModel userModel = new UserModel();
        userModel.setId(user.getId());
        userModel.setEmail(user.getEmail());
        userModel.setNickname(user.getNickname());
        userModel.setAvatar(user.getAvatar());
        userModel.setSignInTime(user.getSignInTime());
        userModel.setUserStatus(user.getUserStatus());
        userModel.setActive(user.isActive());
        userModel.setCreatedAt(user.getCreatedAt());
        userModel.setUpdatedAt(user.getUpdatedAt());
        userModel.setAccountNumber(user.getAccountNumber());
        return userModel;  // Convert User to UserModel
    }

    // Using a simple Set to store blacklisted tokens
    // In a production environment, you may want to use Redis or a database to store blacklisted tokens
    private Set<String> tokenBlacklist = Sets.newConcurrentHashSet();

    /**
     * Adds a token to the blacklist.
     * @param token The token to be blacklisted.
     */
    @Override
    public void addTokenToBlacklist(String token) {
        tokenBlacklist.add(token);  // Add token to blacklist
    }

    private String generateAccountNumber() {
        // 生成 UUID 并去掉横线
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        // 取前 16 位作为账号
        return uuid.substring(0, 16);
    }
    @Override
    public void registerUser(String username, String email, String password, String activationToken) {
        UserModel userModel = new UserModel();
        userModel.setNickname(username);
        userModel.setEmail(email);
        userModel.setUserPassword(passwordEncoder.encode(password));
        userModel.setActivationToken(activationToken);
        userModel.setActive(false);
        userModel.setCreatedAt(new Date());
        userModel.setUpdatedAt(new Date());

        // 生成 accountNumber
        String accountNumber = generateAccountNumber();
        userModel.setAccountNumber(accountNumber);
        userModel.setAvatar("default-avatar.png");

        try {
            int result = userMapper.insertSelective(convertToUser(userModel));
            if (result > 0) {
                logger.info("User registered successfully: {}", userModel.getEmail());
                emailServiceInterface.sendActivationEmail(email, activationToken);
            } else {
                logger.error("Failed to register user: {}", userModel.getEmail());
                throw new RuntimeException("Failed to register user");
            }
        } catch (Exception e) {
            logger.error("Error inserting user: " + email, e);
            throw new RuntimeException("Failed to create user account.");
        }
    }



    @Override
    public void sendActivationEmail(String email, String activationToken) {

    }

    /**
     * Checks if a token is blacklisted.
     * @param token The token to check.
     * @return true if the token is blacklisted, false otherwise.
     */
    public boolean isTokenBlacklisted(String token) {
        return tokenBlacklist.contains(token);  // Check if token is in blacklist
    }



}
