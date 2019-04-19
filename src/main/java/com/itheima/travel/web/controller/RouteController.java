package com.itheima.travel.web.controller;

import com.itheima.travel.domain.PageBean;
import com.itheima.travel.domain.ResultInfo;
import com.itheima.travel.domain.Route;
import com.itheima.travel.service.IRouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * @author LloydLai
 * @version 1.0
 * @Description: 这是处理路线的servlet
 * @date Created in 2019/3/23 12:30
 * @Modified By: TODO
 */
@Controller
@RequestMapping(value = "/route")
public class RouteController {
    private final IRouteService service;

    @Autowired
    public RouteController(IRouteService service) {
        this.service = service;
    }

    /**
     * 此方法获取旅游路线精选
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/findSelection")
    public ResultInfo findSelection() {
        ResultInfo info = new ResultInfo(true);
        try {
            //调用业务层方法，获取人气游list、最新游list、主题游list的map集合
            Map<String, List> map = service.findSelection();
            info.setData(map);
        } catch (Exception e) {
            e.printStackTrace();
            info.setFlag(false);
            info.setErrorMsg(e.getMessage());
        }
        return info;
    }

    /**
     * 此方法是获取某个分类下的路线信息和搜索功能
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/findRouteByCid")
    public ResultInfo findRouteByCid(@RequestParam(value = "cid") Integer cid,
                                     @RequestParam(value = "currentPage") Long currentPage,
                                     @RequestParam(value = "keyword") String keyword
    ) {
        ResultInfo info = new ResultInfo(true);
        info.setFlag(true);
        try {
            PageBean<Route> pageBean = service.findRoutePage(cid, currentPage, keyword);
            info.setData(pageBean);
        } catch (Exception e) {
            e.printStackTrace();
            info.setFlag(false);
            info.setErrorMsg(e.getMessage());
        }

        return info;
    }

    /**
     * 此方法获取收藏排行榜信息
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/findRouteDetail")
    private ResultInfo findRouteDetail(@RequestParam(value = "ris") Integer rid) {
        ResultInfo info = new ResultInfo(true);
        //调用业务层的方法查询rid的路线信息
        try {
            Route route = service.findRouteByRid(rid);
            info.setData(route);
        } catch (Exception e) {
            e.printStackTrace();
            info.setFlag(false);
            info.setErrorMsg(e.getMessage());
        }
        return info;
    }

    @ResponseBody
    @RequestMapping(value = "/favoriteRank")
    public ResultInfo favoriteRank(@RequestParam(value = "currentPage") Long currentPage,
                                   @RequestParam(value = "rname") String rname,
                                   @RequestParam(value = "maxPrice") String maxPrice,
                                   @RequestParam(value = "minPrice") String minPrice) {
        ResultInfo info = new ResultInfo(true);
        try {
            PageBean<Route> pageBean = service.favoriteRank(currentPage, rname, maxPrice, minPrice);
            info.setData(pageBean);
        } catch (Exception e) {
            e.printStackTrace();
            info.setFlag(false);
            info.setErrorMsg(e.getMessage());
        }
        return info;
    }

}
