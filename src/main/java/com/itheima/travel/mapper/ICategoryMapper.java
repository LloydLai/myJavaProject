package com.itheima.travel.mapper;

import com.itheima.travel.domain.Category;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author LloydLai
 * @version 1.0
 * @Description: TODO
 * @date Created in 2019/3/26 08:49
 * @Modified By: TODO
 */
public interface ICategoryMapper {
    @Select("select * from tab_category;")
    List<Category> findAllCategory();
}
