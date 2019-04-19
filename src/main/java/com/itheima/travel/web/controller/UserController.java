package com.itheima.travel.web.controller;


import com.itheima.travel.constants.Constant;
import com.itheima.travel.domain.ResultInfo;
import com.itheima.travel.domain.User;
import com.itheima.travel.exception.ErrorCheckcodeException;
import com.itheima.travel.service.IUserService;
import com.itheima.travel.utils.Md5Util;
import com.itheima.travel.utils.UuidUtil;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;

/**
 * @author LloydLai
 * @version 1.0
 * @Description: 这是处理用户请求的servlet
 * @date Created in 2019/3/23 12:30
 * @Modified By: TODO
 */
@Controller
@RequestMapping(value = "/user")
public class UserController {
    private final IUserService service;

    @Autowired
    public UserController(IUserService service) {
        this.service = service;
    }

    /**
     * 这是校检用户名的方法,异步请求
     */
    @ResponseBody
    @RequestMapping(value = "/checkUsername")
    private ResultInfo checkUsername(@RequestParam(value = "username") String username) {
        //创建一个ResultInfo对象，用来封装响应结果统一响应给客户端
        //true说明服务器为发生异常
        ResultInfo info = new ResultInfo(true);
        User user = null;
        try {
            //调用业务层方法校检用户名
            user = service.findUserByUsername(username);

            if (user == null) {
                //用户名可用
                //true表示用户名可用
                info.setData(true);
            } else {
                //用户名不可用
                //false表示用户名不可用
                info.setData(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
            //说明服务器异常
            info.setFlag(false);
            //设置服务器异常信息
            info.setErrorMsg(e.getMessage());
        }
        return info;
    }

    /**
     * 这是用户注册的方法，异步请求
     *
     * @param request
     */
    @ResponseBody
    @RequestMapping(value = "/register")
    private ResultInfo register(HttpServletRequest request) {
        //获取所有的客户端请求参数
        Map<String, String[]> map = request.getParameterMap();
        System.out.println(map);
        ResultInfo info = new ResultInfo(true);
        User user = new User();
        try {
            BeanUtils.populate(user, map);
            user.setStatus(Constant.UNACTIVE);
            //激活码
            String code = UuidUtil.getUuid();
            user.setCode(code);

            //密码进行加密
            String password = Md5Util.encodeByMd5(user.getPassword());
            user.setPassword(password);

            boolean flag = service.saveUser(user);
            if (flag) {
                info.setData(true);

            } else {
                info.setData(false);
            }

        } catch (Exception e) {
            e.printStackTrace();
            info.setFlag(false);
            info.setErrorMsg(e.getMessage());
        }
        return info;
    }

    /**
     * 此方法激活账号，同步请求
     *
     * @param response
     */
    @ResponseBody
    @RequestMapping(value = "/active")
    public ResultInfo active(@RequestParam(value = "code") String code, HttpServletResponse response) {
        try {
            //调用业务层方法
            User user = service.findUserByCode(code);
            if (user != null) {
                String status = user.getStatus();
                //防止重复激活
                if (status.equals(Constant.ACTIVED)) {
                    response.getWriter().print("请勿重复激活！~");
                } else {
                    //激活用户
                    //更新用户信息
                    user.setStatus(Constant.ACTIVED);
                    boolean flag = service.updateUser(user);
                    if (flag) {
                        response.sendRedirect("login.html");
                    }
                }
            } else {
                response.getWriter().print("激活失败！~");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 此方法处理登录请求，异步请求
     *
     * @param request
     */
    @ResponseBody
    @RequestMapping(value = "/login")
    public ResultInfo login(@RequestParam(value = "checkCode") String checkCode,
                            @RequestParam(value = "username") String username,
                            @RequestParam(value = "password") String password,
                            HttpServletRequest request) {
        //创建session获取验证码
        HttpSession session = request.getSession();
        String code = (String) session.getAttribute(Constant.CHECKCODE_KEY);

        ResultInfo info = new ResultInfo(true);
        try {
            if (code.equalsIgnoreCase(checkCode)) {
                //调用业务层方法校检用户登录,出现错误则抛异常
                //验证码正确
                User user = service.checkLogin(username, password);
                //登录成功后
                //将user对象存储到session中
                session.setAttribute(Constant.USER_KEY, user);
                info.setFlag(true);
                info.setData(true);
            } else {
                //验证码错误
                throw new ErrorCheckcodeException("验证码输入错误");
            }
        } catch (Exception e) {
            e.printStackTrace();
            info.setData(false);
            info.setErrorMsg(e.getMessage());
        }
        return info;
    }

    /**
     * 获取用户是否登录的信息，异步请求
     *
     * @param request
     */
    @ResponseBody
    @RequestMapping(value = "/isLogin")
    public ResultInfo isLogin(HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(Constant.USER_KEY);

        ResultInfo info = new ResultInfo(true);
        info.setData(user);
        return info;
    }

    /**
     * 此方法退出登录，同步请求
     *
     * @param request
     * @param response
     * @throws IOException
     */
    @ResponseBody
    @RequestMapping(value = "/loginOut")
    public ResultInfo loginOut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //销毁session
        HttpSession session = request.getSession();
        session.invalidate();
        //跳转到登录界面
        response.sendRedirect("login.html");
        return null;
    }
}
