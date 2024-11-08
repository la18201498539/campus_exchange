package edu.bu.cs673.secondhand.service;

import edu.bu.cs673.secondhand.model.UserModel;

/***
 Email: qyyh@bu.edu,la1993@bu.edu
 DateTime: 11/3/24-14:03
 *****/
public interface UserService {
    /**
     * Register a new user.
     * @param user The user to register.
     * @return true if registration is successful, false otherwise.
     */
    boolean registerUser(UserModel user);

    /**
     * Activate a user account.
     * @param email The user's email.
     * @param activationToken The activation token.
     * @return true if activation is successful, false otherwise.
     */
    boolean activateUser(String email, String activationToken);

    /**
     * Authenticate a user.
     * @param email The user's email.
     * @param password The user's password.
     * @return The authenticated UserModel if successful, null otherwise.
     */
    UserModel login(String email, String password);

    /**
     * Initiate the password reset process.
     * @param email The user's email.
     * @return true if password reset process is initiated successfully, false otherwise.
     */
    boolean initiatePasswordReset(String email);

    /**
     * Reset the user's password.
     * @param email The user's email.
     * @param resetToken The reset token.
     * @param newPassword The new password.
     * @return true if password is reset successfully, false otherwise.
     */
    boolean resetPassword(String email, String resetToken, String newPassword);

    /**
     * Initiate the sign-up process.
     * @param email The user's email.
     */
    void initiateSignUp(String email);

    /**
     * Complete the sign-up process.
     * @param email The user's email.
     * @param token The activation token.
     * @param password The user's password.
     * @param nickname The user's nickname.
     */
    void completeSignUp(String email, String token, String password, String nickname);

    /**
     * Get a user by email.
     * @param email The user's email.
     * @return The UserModel if found, null otherwise.
     */
    UserModel getUserByEmail(String email);

    /**
     * Updates user information.
     * @param userModel The user information.
     * @return true if the update is successful, false otherwise.
     */
    boolean updateUser(UserModel userModel);

    /**
     * Update user's avatar.
     * @param email The user's email.
     * @param avatarUrl The new avatar URL.
     * @return true if update is successful, false otherwise.
     */
    boolean updateAvatar(String email, String avatarUrl);

    /**
     * Get user's account number.
     * @param email The user's email.
     * @return The account number if found, null otherwise.
     */
    String getAccountNumber(String email);

    /**
     * Check if a token has expired.
     * @param email The user's email.
     * @param token The token to check.
     * @return true if the token has expired, false otherwise.
     */
    boolean isTokenExpired(String email, String token);

    /**
     * Add a token to the blacklist.
     * @param token The token to be blacklisted.
     */
    void addTokenToBlacklist(String token);

    void registerUser(String username, String email, String password, String activationToken);

    void sendActivationEmail(String email, String activationToken);


}
