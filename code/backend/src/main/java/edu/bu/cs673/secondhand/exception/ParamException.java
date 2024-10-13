package edu.bu.cs673.secondhand.exception;

import java.util.Map;

/***
 Email: ybinman@bu.edu
 DateTime: 10/12/24-22:24
 *****/
public class ParamException extends RuntimeException{
    private Map map;

    public ParamException(Map map) {
        this.map = map;
    }

    public Map getMap() {
        return map;
    }
}
