package com.itheima.travel.service.impl;

import com.itheima.travel.constants.Constant;
import com.itheima.travel.mapper.IRouteMapper;
import com.itheima.travel.domain.*;
import com.itheima.travel.service.IRouteService;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author LloydLai
 * @version 1.0
 * @Description: 这是处理路线的service
 * @date Created in 2019/3/26 15:46
 * @Modified By: TODO
 */
@Service("routeService")
public class RouteServiceImpl implements IRouteService {
    private final IRouteMapper routeDao;

    @Autowired
    public RouteServiceImpl(IRouteMapper routeDao) {
        this.routeDao = routeDao;
    }

    @Override
    public Map<String, List> findSelection() throws Exception {
        Map<String, List> map = new HashMap<>(16);
        //调用dao层方法查找人气旅游路线
        List<Route> popList = routeDao.findPopList();
        //调用dao层方法查找最新旅游路线
        List<Route> newestList = routeDao.findNewestList();
        //调用dao层方法查找主题旅游路线
        List<Route> themeList = routeDao.findThemeList();

        map.put("pop", popList);
        map.put("newest", newestList);
        map.put("theme", themeList);
        return map;
    }

    @Override
    public PageBean<Route> findRoutePage(Integer cid, Long currentPage, String keyword) throws Exception {
        //新建pageBean对象，分别将数据封装到对象中
        PageBean<Route> pageBean = new PageBean<>();
        //当前页
        pageBean.setCurrentPage(currentPage);
        //规定每页显示条数
        Integer pageSize = Constant.ROUTE_PAGESIZE;
        pageBean.setPageSize(pageSize);
        if (keyword != null && !"null".equals(keyword) && !"".equals(keyword)) {
            keyword = "%" + keyword + "%";
        }
        //总数据条数
        Long totalSize = routeDao.findCountByCid(cid, keyword);
        pageBean.setTotalSize(totalSize);
        //路线集合
        currentPage = (currentPage - 1) * pageSize;
        List<Route> list = routeDao.findPageRoutes(currentPage, pageSize, cid, keyword);
        pageBean.setList(list);

        return pageBean;
    }

    //分表查询
    /*@Override
    public Route findRouteByRid(String rid) throws Exception {
        //将查询到所有的信息封装到route中
        //调用dao方法查询tab_route表获取route信息
        Route route = routeDao.findCountByRid(rid);

        //调用dao方法查询tab_category表根据cid获取分类信息
        Category category = routeDao.findCategoryByCid(route.getCid());
        route.setCategory(category);

        //调用dao方法查询tab_seller表根据sid查询商户信息
        Seller seller = routeDao.findSellerBySid(route.getSid());
        route.setSeller(seller);

        //调用dao方法查询tab_route_img表根据rid查询路线图片信息
        List<RouteImg> routeImgList = routeDao.findRouteImgByRid(rid);
        route.setRouteImgList(routeImgList);

        return route;
    }*/

    //多表连接查询
    @Override
    public Route findRouteByRid(Integer rid) throws Exception {
        //将查询到所有的信息封装到route中
        //创建一个Route对象
        Route route = new Route();
        //调用dao方法获取数据集合封装到Route对象中
        Map<String, Object> map = routeDao.findRouteByRid(rid);
        BeanUtils.populate(route, map);

        //创建Category对象，封装后set到route中
        Category category = new Category();
        BeanUtils.populate(category, map);
        route.setCategory(category);

        //创建Seller对象，封装后set到route中
        Seller seller = new Seller();
        BeanUtils.populate(seller, map);
        route.setSeller(seller);

        //调用dao方法查询tab_route_img表根据rid查询路线图片信息
        List<RouteImg> routeImgList = routeDao.findRouteImgByRid(rid);
        route.setRouteImgList(routeImgList);

        return route;
    }

    @Override
    public PageBean<Route> favoriteRank(Long currentPage, String rname, String maxPrice, String minPrice) throws Exception {
        PageBean<Route> pageBean = new PageBean<>();
        pageBean.setCurrentPage(currentPage);
        if (rname != null && !"null".equals(rname) && !"".equals(rname)) {
            rname = "%" + rname + "%";
        }
        Integer pageSize = Constant.ROUTE_PAGESIZE;
        pageBean.setPageSize(pageSize);
        Long totalSize = routeDao.findRouteCount(rname, maxPrice, minPrice);
        pageBean.setTotalSize(totalSize);

        currentPage = (currentPage - 1) * pageSize;
        List<Route> list = routeDao.findPageRouteByCount(currentPage, pageSize, rname, maxPrice, minPrice);
        pageBean.setList(list);

        return pageBean;
    }
}
