package edu.bu.cs673.secondhand.service;

import edu.bu.cs673.secondhand.domain.User;
import edu.bu.cs673.secondhand.vo.PageVo;
import org.apache.ibatis.annotations.Param;

/***
 Email: qyyh@bu.edu,la1993@bu.edu
 DateTime: 11/3/24-14:03
 *****/

public interface UserServiceLegacy {

    /**
     *
     * @param id
     * @return
     */
    User getUser(Long id);

    /**
     *
     * @param email
     * @param userPassword
     * @return
     */
    User userLogin(@Param("email") String email, String userPassword);

    /**
     *
     * @param user
     * @return
     */
    boolean userSignIn(User user);

    /**
     *
     * @param user
     * @return
     */
    boolean updateUserInfo(User user);

    /**
     *
     * @param newPassword
     * @param oldPassword
     * @param id
     * @return
     */
    boolean updatePassword(String newPassword, String oldPassword,Long id);

    /**
     *
     * @param status
     * @param page
     * @param nums
     * @return
     */
    PageVo<User> getUserByStatus(int status, int page , int nums);


    /**
     *
     * @param nickname
     * @return
     */
    Long getUserId(String nickname);


    /**
     *
     * @param searchValue
     * @param mode
     * @return
     */
    PageVo<User> getUserByNumber(String searchValue, int mode);

    void sendVerificationEmail(User user);

    boolean activateUserByToken(String token);

    public boolean verifyCode(User user);

    public boolean insertActiveCode(String email);

    /**
     * Sends a simple email message.
     * @param to The recipient's email address.
     * @param subject The subject of the email.
     * @param text The body of the email.
     */
    void sendSimpleMessage(String to, String subject, String text);

    /**
     * Sends a sign-up email to the user with a link to complete the sign-up process.
     * @param user The activation token for the sign-up process.
     */
    void sendSignUpEmail(User user);

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
