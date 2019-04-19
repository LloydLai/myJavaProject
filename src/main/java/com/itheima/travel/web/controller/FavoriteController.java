package com.itheima.travel.web.controller;

import com.itheima.travel.constants.Constant;
import com.itheima.travel.domain.Favorite;
import com.itheima.travel.domain.PageBean;
import com.itheima.travel.domain.ResultInfo;
import com.itheima.travel.domain.User;
import com.itheima.travel.service.IFavoriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author LloydLai
 * @version 1.0
 * @Description: 这是处理收藏功能的servlet
 * @date Created in 2019/3/23 12:30
 * @Modified By: TODO
 */
@Controller
@RequestMapping(value = "/favorite")
public class FavoriteController {
    private final IFavoriteService service;

    @Autowired
    public FavoriteController(IFavoriteService service) {
        this.service = service;
    }

    /**
     * 此方法查询用户是否已收藏某条旅游路线
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/isFavorite")
    public ResultInfo isFavorite(@RequestParam(value = "rid") Integer rid, HttpServletRequest request) {
        ResultInfo info = new ResultInfo(true);
        //判断当前是否已登录
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(Constant.USER_KEY);
        try {
            if (user != null) {
                //已登录，调用service方法查询是否已收藏
                Favorite favorite = service.findFavorite(rid, user);
                //判断是否已经收藏
                if (favorite != null) {
                    //已经收藏
                    info.setData(true);
                } else {
                    //未收藏
                    info.setData(false);
                }
            } else {
                //未登录，肯定未收藏
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
     * 此方法添加收藏
     *
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/addFavorite")
    public ResultInfo addFavorite(@RequestParam(value = "rid") Integer rid, HttpServletRequest request) {
        ResultInfo info = new ResultInfo(true);
        //判断当前是否登录
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(Constant.USER_KEY);

        try {
            if (user != null) {
                //已登录，调用service方法添加并最终获取收藏次数
                Integer count = service.addFavorite(rid, user);
                info.setData(count);
            } else {
                //未登录，告诉客户端跳转到登录界面
                info.setData(-1);
            }
        } catch (Exception e) {
            e.printStackTrace();
            info.setFlag(false);
            info.setErrorMsg(e.getMessage());
        }
        return info;
    }

    /**
     * 此方法展示收藏栏
     *
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("/showMyFavorite")
    public ResultInfo showMyFavorite(@RequestParam(value = "currentPage") Long currentPage, HttpServletRequest request) {
        ResultInfo info = new ResultInfo(true);
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(Constant.USER_KEY);

        try {
            //页面展示、分页功能等需要封装带pageBean对象中
            PageBean<Favorite> pageBean = service.findMyFavorites(currentPage, user);
            info.setData(pageBean);
        } catch (Exception e) {
            e.printStackTrace();
            info.setFlag(false);
            info.setErrorMsg(e.getMessage());
        }
        return info;
    }

    /**
     * 取消收藏方法
     *
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/removeFavorite")
    public ResultInfo removeFavorite(@RequestParam(value = "rid") Integer rid,HttpServletRequest request) {
        ResultInfo info = new ResultInfo(true);

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(Constant.USER_KEY);
        Integer count = service.removeFavorite(rid, user);

        return info;
    }
}
