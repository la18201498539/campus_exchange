package edu.bu.cs673.secondhand.enums;

/***
 Email: ybinman@bu.edu
 DateTime: 10/6/24-13:54
 *****/
public enum ErrorMsg {

    PARAM_ERROR("Invalid Parameters"),
    MISSING_PARAMETER("Missing parameters"),
    SYSTEM_ERROR("System Error"),
    FILE_UPLOAD_ERROR("Upload Error"),
    REGISTER_ERROR("Register Error"),
    EMAIL_LOGIN_ERROR("User Not Found"),
    ACCOUNT_Ban("illegal User"),

    FAVORITE_EXIT("Duplicate Favorites"),
    PASSWORD_RESET_ERROR("Reset password fail"),
    COOKIE_ERROR("Invalid Cookie"),
    UNKNOWN_ERROR("Unknown Error");



    private String msg;

    ErrorMsg(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }
}
