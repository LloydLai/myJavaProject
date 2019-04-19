package com.itheima.travel.service.impl;


import com.itheima.travel.constants.Constant;
import com.itheima.travel.mapper.IUserMapper;
import com.itheima.travel.domain.User;
import com.itheima.travel.exception.ErrorPasswordException;
import com.itheima.travel.exception.ErrorUsernameException;
import com.itheima.travel.exception.UnactiveException;
import com.itheima.travel.service.IUserService;
import com.itheima.travel.utils.MailUtil;
import com.itheima.travel.utils.Md5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author LloydLai
 * @version 1.0
 * @Description: 这是IUserService的实现类
 * @date Created in 2019/3/21 21:32
 * @Modified By: TODO
 */
@Service("userService")
public class UserServiceImpl implements IUserService {
    private final IUserMapper userDao;

    @Autowired
    public UserServiceImpl(IUserMapper userDao) {
        this.userDao = userDao;
    }

    @Override
    public User findUserByUsername(String username) {
        User user = userDao.findUserByUsername(username);
        return user;
    }

    @Override
    public boolean saveUser(User user) throws Exception {
        boolean flag = userDao.saveUser(user);

        //保存成功则发送激活邮件给客户端
        if (flag) {
            MailUtil.sendMail(user.getEmail(), "欢迎" + user.getName() + "注册黑马旅游网,<a href='http://localhost:8080/travel/user?action=active&code=" + user.getCode() + "'>点击激活</a>");
        }

        return flag;
    }

    @Override
    public User findUserByCode(String code) throws Exception {
        //激活操作实际上就是修改用户的status字段，改成Y
        //根据code获取对应的用户信息
        User user = userDao.findUserByCode(code);
        return user;
    }

    /**
     * 此方法执行更新user信息的操作
     *
     * @return
     * @throws Exception
     */
    @Override
    public boolean updateUser(User user) throws Exception {
        boolean flag = userDao.updateUser(user);
        return flag;
    }

    @Override
    public User checkLogin(String username, String password) throws Exception {
        //区分出是哪个错误
        User user = userDao.findUserByUsername(username);
        if (user != null) {
            //用户名正确
            //校验密码,md5无法逆转，将传入的密码加密与数据库中的对比
            password = Md5Util.encodeByMd5(password);
            String pwd = user.getPassword();
            if (pwd.equals(password)) {
                //密码正确
                String status = user.getStatus();
                if (status.equals(Constant.ACTIVED)) {
                    //已激活
                    return user;
                } else {
                    //未激活
                    throw new UnactiveException("用户未激活");
                }
            } else {
                //密码错误
                throw new ErrorPasswordException("密码错误");
            }
        } else {
            //用户名错误
            throw new ErrorUsernameException("用户名错误");
        }
    }


}
