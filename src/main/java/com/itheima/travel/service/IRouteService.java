package com.itheima.travel.service;

import com.itheima.travel.domain.PageBean;
import com.itheima.travel.domain.Route;

import java.util.List;
import java.util.Map;

/**
 * @author LloydLai
 * @version 1.0
 * @Description: TODO
 * @date Created in 2019/3/26 15:47
 * @Modified By: TODO
 */
public interface IRouteService {
    Map<String, List> findSelection() throws Exception;

    PageBean<Route> findRoutePage(Integer cid, Long currentPage, String keyword) throws Exception;

    Route findRouteByRid(Integer rid) throws Exception;

    PageBean<Route> favoriteRank(Long currentPage, String rname, String maxPrice, String minPrice) throws Exception;
}
