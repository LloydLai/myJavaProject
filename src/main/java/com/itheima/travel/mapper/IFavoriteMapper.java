package com.itheima.travel.mapper;

import com.itheima.travel.domain.Favorite;
import com.itheima.travel.domain.User;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

/**
 * @author LloydLai
 * @version 1.0
 * @Description: TODO
 * @date Created in 2019/3/27 13:22
 * @Modified By: TODO
 */
public interface IFavoriteMapper {

    @Select("select * from tab_favorite where rid = #{rid} and uid = #{user.uid};")
    Favorite findFavorite(@Param("rid") Integer rid, @Param("user") User user);

    @Insert("insert into tab_favorite values(#{route}, #{date}, #{user});")
    void addFavorite(Integer rid, User user);

    @Update("update tab_route set count = count + 1 where rid = #{rid};")
    void updateRouteCount(Integer rid);

    @Select("select count from tab_route where rid = #{rid};")
    Integer findRouteCountByRid(Integer rid);

    @Select("select count(*) from tab_favorite where uid = #{uid};")
    Long findMyFavoriteCount(User user);

    @Select("select * from tab_favorite f, tab_route r where f.rid = r.rid and uid = #{user.uid} limit #{currentPage}, #{pageSize};")
    List<Map<String, Object>> findMyFavorites(User user, Long currentPage, Integer pageSize);

    @Update("")
    void removeFavorite(Integer rid, User user);

    @Update("")
    void lessFavoriteRouteCount(Integer rid);
}
