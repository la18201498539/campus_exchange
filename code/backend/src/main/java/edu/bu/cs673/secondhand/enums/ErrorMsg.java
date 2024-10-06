package edu.bu.cs673.secondhand.enums;

/***
 Email: ybinman@bu.edu
 DateTime: 10/6/24-13:54
 *****/
public enum ErrorMsg {

    PARAM_ERROR("Invalid Parameters"),
    SYSTEM_ERROR("System Error"),
    UNKNOWN_ERROR("Unknown Error");

    private String msg;

    ErrorMsg(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }
}
