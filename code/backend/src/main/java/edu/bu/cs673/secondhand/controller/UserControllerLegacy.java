package edu.bu.cs673.secondhand.controller;

import edu.bu.cs673.secondhand.domain.User;
import edu.bu.cs673.secondhand.enums.ErrorMsg;
import edu.bu.cs673.secondhand.service.UserServiceLegacy;
import edu.bu.cs673.secondhand.vo.ResultVo;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.logging.Logger;

/***
 Email: qyyh@bu.edu,la1993@bu.edu
 DateTime: 11/3/24-14:03
 *****/
@RestController
@RequestMapping("/user")
public class UserControllerLegacy {

    private static final Logger logger = Logger.getLogger(UserControllerLegacy.class.getName());

    @Autowired
    private UserServiceLegacy userService;


    /**
     * @param userModel
     * @return
     */
    @PostMapping("register")
    public ResultVo signIn(@RequestBody User userModel) {
        logger.info(userModel.toString());

        userModel.setSignInTime(new Timestamp(System.currentTimeMillis()));
        if (userModel.getAvatar() == null || "".equals(userModel.getAvatar())) {
            userModel.setAvatar("https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png");
        }
        if (userService.userSignIn(userModel)) {
            userService.insertActiveCode(userModel.getEmail());
            userService.sendSignUpEmail(userModel);
            System.out.println(userModel.getEmail()+"Email has been sent");
            return ResultVo.success(userModel);
        }
        return ResultVo.fail(ErrorMsg.REGISTER_ERROR);
    }

    @PostMapping("/verifyCode")
    public ResultVo verifyCode(@RequestBody User userModel) {
        System.out.println("Email is "+userModel.getEmail() +"code is" + userModel.getActiveCode());
        boolean isVerified = userService.verifyCode(userModel);
        System.out.println(isVerified);
        if (isVerified) {
            return ResultVo.success("true");
        } else {
            return ResultVo.fail("Invalid verification code.");
        }
    }
    
    /**
     *
     * @param email
     * @param userPassword
     * @param response
     * @return
     */
    @RequestMapping("login")
    public ResultVo login(@RequestParam("email") @NotEmpty @NotNull String email,
                          @RequestParam("userPassword") @NotEmpty @NotNull String userPassword,
                          HttpServletResponse response) {
        logger.info("Login: " + email);
        logger.info("===================================");
        logger.info(email + "   " + userPassword);
        logger.info("===================================");
        User userModel = userService.userLogin(email, userPassword);

        if (null == userModel) {
            return ResultVo.fail(ErrorMsg.EMAIL_LOGIN_ERROR);
        }

        if(email.equals("") || userPassword.equals("")){
            return ResultVo.fail(ErrorMsg.EMAIL_LOGIN_ERROR);
        }
        if(userModel.getUserStatus()!=null&&userModel.getUserStatus().equals((byte) 1)){
            return ResultVo.fail(ErrorMsg.ACCOUNT_Ban);
        }
        Cookie cookie = new Cookie("shUserId", String.valueOf(userModel.getId()));
        cookie.setPath("/");
        cookie.setHttpOnly(false);
        response.addCookie(cookie);
        return ResultVo.success(userModel);
    }

    /**
     *
     * @param shUserId
     * @param response
     * @return
     */
    @RequestMapping("logout")
    public ResultVo logout(@CookieValue("shUserId")
                           @NotNull(message = "Login fail, try again.")
                           @NotEmpty(message = "Login fail, try again.") String shUserId, HttpServletResponse response) {
        Cookie cookie = new Cookie("shUserId", shUserId);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        response.addCookie(cookie);
        return ResultVo.success();
    }

    /**
     *
     * @param id
     * @return
     */
    @GetMapping("info")
    public ResultVo getOneUser(@CookieValue("shUserId") @NotNull(message = "Login fail, try again.")
                               @NotEmpty(message = "Login fail, try again.")
                               String id) {
        return ResultVo.success(userService.getUser(Long.valueOf(id)));
    }

    /**
     *
     * @param id
     * @param userModel
     * @return
     */
    @PostMapping("/info")
    public ResultVo updateUserPublicInfo(@CookieValue("shUserId") @NotNull(message = "Login fail, try again.")
                                         @NotEmpty(message = "Login fail, try again.")
                                         String id, @RequestBody  User userModel) {
        userModel.setId(Long.valueOf(id));
        if (userService.updateUserInfo(userModel)) {
            return ResultVo.success();
        }
        return ResultVo.fail(ErrorMsg.SYSTEM_ERROR);
    }


    /**
     *
     * @param id
     * @param oldPassword
     * @param newPassword
     * @return
     */
    @GetMapping("/password")
    public ResultVo updateUserPassword(@CookieValue("shUserId") @NotNull(message = "Login fail, try again.")
                                       @NotEmpty(message = "Login fail, try again.") String id,
                                       @RequestParam("oldPassword") @NotEmpty @NotNull String oldPassword,
                                       @RequestParam("newPassword") @NotEmpty @NotNull String newPassword) {
        if (userService.updatePassword(newPassword,oldPassword,Long.valueOf(id))) {
            return ResultVo.success();
        }
        return ResultVo.fail(ErrorMsg.PASSWORD_RESET_ERROR);
    }

//    @GetMapping("/activate")
//    public ResponseEntity<String> activateUser(@RequestParam("token") String token) {
//        boolean activated = userService.activateUserByToken(token);
//        if (activated) {
//            return ResponseEntity.ok("账号激活成功！");
//        } else {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("激活失败或链接已失效");
//        }
//    }

}
