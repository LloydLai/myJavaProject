package com.itheima.travel.exception;

/**
 * @author LloydLai
 * @version 1.0
 * @Description: TODO
 * @date Created in 2019/3/25 15:38
 * @Modified By: TODO
 */
public class UnactiveException extends RuntimeException{
    public UnactiveException(String message) {
        super(message);
    }
}
