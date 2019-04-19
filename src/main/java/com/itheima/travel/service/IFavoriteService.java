package com.itheima.travel.service;

import com.itheima.travel.domain.Favorite;
import com.itheima.travel.domain.PageBean;
import com.itheima.travel.domain.User;

/**
 * @author LloydLai
 * @version 1.0
 * @Description: TODO
 * @date Created in 2019/3/27 13:24
 * @Modified By: TODO
 */
public interface IFavoriteService {
    Favorite findFavorite(Integer rid, User user) throws Exception;

    Integer addFavorite(Integer rid, User user);

    PageBean<Favorite> findMyFavorites(Long currentPage, User user) throws Exception;

    Integer removeFavorite(Integer rid, User user);
}
