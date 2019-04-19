package com.itheima.travel.service;


import com.itheima.travel.domain.User;

/**
 * @author LloydLai
 * @version 1.0
 * @Description: TODO
 * @date Created in 2019/3/21 21:28
 * @Modified By: TODO
 */
public interface IUserService {
    User findUserByUsername(String username) throws Exception;

    boolean saveUser(User user) throws Exception;

    User findUserByCode(String code) throws Exception;

    boolean updateUser(User user) throws Exception;

    User checkLogin(String username, String password) throws Exception;
}
