package com.itheima.travel.service.impl;

import com.itheima.travel.constants.Constant;
import com.itheima.travel.mapper.IFavoriteMapper;
import com.itheima.travel.domain.Favorite;
import com.itheima.travel.domain.PageBean;
import com.itheima.travel.domain.Route;
import com.itheima.travel.domain.User;
import com.itheima.travel.service.IFavoriteService;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author LloydLai
 * @version 1.0
 * @Description: 处理收藏功能的service
 * @date Created in 2019/3/27 13:24
 * @Modified By: TODO
 */
@Service("favoriteService")
@Transactional(
        rollbackFor = java.lang.Exception.class,
        isolation = Isolation.DEFAULT,
        propagation = Propagation.REQUIRED,
        timeout = -1
)
public class FavoriteServiceImpl implements IFavoriteService {
    private final IFavoriteMapper favoriteMapper;

    @Autowired
    public FavoriteServiceImpl(IFavoriteMapper favoriteDao) {
        this.favoriteMapper = favoriteDao;
    }

    @Override
    public Favorite findFavorite(Integer rid, User user) {
        Favorite favorite = favoriteMapper.findFavorite(rid, user);
        return favorite;
    }

    @Override
    public Integer addFavorite(Integer rid, User user) {
        Integer count = 0;
        favoriteMapper.addFavorite(rid, user);
        //往tab_route中count+1
        favoriteMapper.updateRouteCount(rid);
        //查询最终的count数返回servlet
        count = favoriteMapper.findRouteCountByRid(rid);
        return count;
    }

    @Override
    public PageBean<Favorite> findMyFavorites(Long currentPage, User user) throws Exception {
        //创建pageBean对象,favorite中属性有route rid date user 调用dao方法补全数据
        PageBean<Favorite> pageBean = new PageBean<>();

        pageBean.setCurrentPage(currentPage);

        Integer pageSize = Constant.FAVORITE_KEY;
        pageBean.setPageSize(pageSize);

        //调用dao层方法查询总数据条数
        Long totalSize = favoriteMapper.findMyFavoriteCount(user);
        pageBean.setTotalSize(totalSize);

        //查询list<Favorite>返回map数据集合List<Map<String, Object>>
        currentPage = (currentPage - 1) * pageSize;
        List<Map<String, Object>> maps = favoriteMapper.findMyFavorites(user, currentPage, pageSize);
        //处理list集合将数据封装到list中再封装到pageBean中
        List<Favorite> list = new ArrayList<>();
        for (Map<String, Object> map : maps) {
            //封装favorite
            Favorite favorite = new Favorite();
            BeanUtils.populate(favorite, map);

            //封装route
            Route route = new Route();
            BeanUtils.populate(route, map);
            favorite.setRoute(route);

            list.add(favorite);
        }
        pageBean.setList(list);

        return pageBean;
    }

    @Override
    public Integer removeFavorite(Integer rid, User user) {
        favoriteMapper.removeFavorite(rid, user);

        favoriteMapper.lessFavoriteRouteCount(rid);
        return null;
    }
}
