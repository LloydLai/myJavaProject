package com.itheima.travel.mapper;

import com.itheima.travel.domain.Category;
import com.itheima.travel.domain.Route;
import com.itheima.travel.domain.RouteImg;
import com.itheima.travel.domain.Seller;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * @author LloydLai
 * @version 1.0
 * @Description: TODO
 * @date Created in 2019/3/26 15:45
 * @Modified By: TODO
 */
public interface IRouteMapper {
    @Select("select * from tab_route where rflag = 1 order by count desc limit 0, 4;")
    List<Route> findPopList();

    @Select("select * from tab_route where rflag = 1 order by rdate desc limit 0, 4;")
    List<Route> findNewestList();

    @Select("select * from tab_route where rflag = 1 and isThemeTour = 1 limit 0, 4;")
    List<Route> findThemeList();

    Long findCountByCid(Integer cid, String keyword);

    List<Route> findPageRoutes(Long currentPage, Integer pageSize, Integer cid, String keyword);

    @Select("select * from tab_route where rid = #{rid};")
    Route findCountByRid(Integer rid);

    @Select("select * from tab_category where cid = #{cid};")
    Category findCategoryByCid(Integer cid);

    @Select("select * from tab_seller where sid = #{sid};")
    Seller findSellerBySid(Integer sid);

    @Select("select * from tab_route_img where rid = #{rid};")
    List<RouteImg> findRouteImgByRid(Integer rid);

    //连接route、category、seller表进行查询返回一个带数据的map集合
    @Select("select * from tab_route r, tab_category c, tab_seller s where r.cid = c.cid and r.sid = s.sid and rid = #{rid};")
    Map<String, Object> findRouteByRid(Integer rid);

    Long findRouteCount(String rname, String maxPrice, String minPrice);

    List<Route> findPageRouteByCount(Long currentPage, Integer pageSize, String rname, String maxPrice, String minPrice);
}
