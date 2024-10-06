package edu.bu.cs673.secondhand.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import edu.bu.cs673.secondhand.enums.ErrorMsg;

/***
 Email: ybinman@bu.edu
 DateTime: 10/6/24-13:53
 *****/
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class ResultVo<T> {

    private Integer status_code;
    private String msg;
    private T data;

    public static ResultVo success(){
        ResultVo resultVo=new ResultVo();
        resultVo.setStatus_code(1);
        return resultVo;
    }

    public static <T>ResultVo success(T data){
        ResultVo<T> resultVo=new ResultVo<>();
        resultVo.setStatus_code(1);
        resultVo.setData(data);
        return resultVo;
    }

    public static ResultVo fail(ErrorMsg errorMsg){
        ResultVo resultVo=new ResultVo();
        resultVo.setStatus_code(0);
        resultVo.setMsg(errorMsg.getMsg());
        return resultVo;
    }

    public static <T>ResultVo fail(ErrorMsg errorMsg,T data){
        ResultVo<T> resultVo=new ResultVo<>();
        resultVo.setStatus_code(0);
        resultVo.setMsg(errorMsg.getMsg());
        resultVo.setData(data);
        return resultVo;
    }


    public Integer getStatus_code() {
        return status_code;
    }

    public void setStatus_code(Integer status_code) {
        this.status_code = status_code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public ResultVo(Integer status_code, String msg, T data) {
        this.status_code = status_code;
        this.msg = msg;
        this.data = data;
    }

    public ResultVo() {
    }
}
