package edu.bu.cs673.secondhand.service;

import edu.bu.cs673.secondhand.domain.User;
import edu.bu.cs673.secondhand.vo.PageVo;
import org.apache.ibatis.annotations.Param;

/***
 Email: ybinman@bu.edu
 DateTime: 10/15/24-15:44
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
}
