package edu.bu.cs673.secondhand.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import edu.bu.cs673.secondhand.enums.ErrorMsg;

/**
 * ResultVo is a value object that encapsulates the response structure
 * for API calls, including status code, message, and data.
 * Author: YQ
 */
@JsonInclude(value = JsonInclude.Include.NON_NULL)  // Exclude null fields from JSON serialization
public class ResultVo<T> {

    @JsonProperty("status_code")
    private Integer statusCode;  // Status code of the response
    private String msg;  // Message providing additional information
    private T data;  // Data returned in the response

    // Private constructor to enforce the use of static factory methods
    public ResultVo(Integer statusCode, String msg, T data) {
        this.statusCode = statusCode;
        this.msg = msg;
        this.data = data;
    }

    public ResultVo() {
    }

    /**
     * Creates a success response without data.
     * @return A ResultVo representing a successful response.
     */
    public static <T> ResultVo<T> success() {
        return new ResultVo<>(1, "Success", null);  // Default success message
    }

    /**
     * Creates a success response with data.
     * @param data The data to include in the response.
     * @return A ResultVo representing a successful response with data.
     */
    public static <T> ResultVo<T> success(T data) {
        return new ResultVo<>(1, "Success", data);  // Default success message
    }

    /**
     * Creates a success response with a custom message and data.
     * @param message The success message.
     * @param data The data to include in the response.
     * @return A ResultVo representing a successful response with a message and data.
     */
    public static <T> ResultVo<T> success(String message, T data) {
        return new ResultVo<>(1, message, data);  // Default success message
    }

    /**
     * Creates a failure response based on an ErrorMsg.
     * @param errorMsg The error message to include in the response.
     * @return A ResultVo representing a failed response.
     */
    public static <T> ResultVo<T> fail(ErrorMsg errorMsg) {
        return new ResultVo<>(0, errorMsg.getMsg(), null);  // Default failure message
    }

    /**
     * Creates a failure response with a custom message.
     * @param message The failure message to include in the response.
     * @return A ResultVo representing a failed response.
     */
    public static <T> ResultVo<T> fail(String message) {
        ResultVo<T> resultVo = new ResultVo<>();
        resultVo.setStatusCode(400);  // or whatever code you use for errors
        resultVo.setMsg(message);
        return resultVo;
    }

    /**
     * Creates a failure response with additional data.
     * @param errorMsg The error message to include in the response.
     * @param data The data to include in the response.
     * @return A ResultVo representing a failed response with data.
     */
    public static <T> ResultVo<T> fail(ErrorMsg errorMsg, T data) {
        return new ResultVo<>(0, errorMsg.getMsg(), data);  // Default failure message
    }

    // Getters and setters
    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
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
}
