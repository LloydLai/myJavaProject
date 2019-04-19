package com.itheima.travel.exception;

/**
 * @author LloydLai
 * @version 1.0
 * @Description: TODO
 * @date Created in 2019/3/25 15:35
 * @Modified By: TODO
 */
public class ErrorUsernameException extends RuntimeException {
    public ErrorUsernameException(String message) {
        super(message);
    }
}
