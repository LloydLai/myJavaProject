package com.itheima.travel.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itheima.travel.constants.Constant;
import com.itheima.travel.mapper.ICategoryMapper;
import com.itheima.travel.domain.Category;
import com.itheima.travel.service.ICategoryService;
import com.itheima.travel.utils.JedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import java.util.List;

/**
 * @author LloydLai
 * @version 1.0
 * @Description: 首页内容类别的service
 * @date Created in 2019/3/26 08:48
 * @Modified By: TODO
 */
@Service("categoryService")
public class CategoryServiceImpl implements ICategoryService {
    private final ICategoryMapper categoryDao;

    @Autowired
    public CategoryServiceImpl(ICategoryMapper categoryDao) {
        this.categoryDao = categoryDao;
    }

    @Override
    public String findAllCategory() throws Exception {
        Jedis jedis = JedisUtil.getJedis();
        String allCategory = jedis.get(Constant.ALLCATEGORY_KEY);
        //如果all_category为空，说明redis中没有分类列表数据
        if (allCategory == null || "".equals(allCategory)) {
            //最先到数据库拿到数据
            List<Category> categories = categoryDao.findAllCategory();
            //转换为json数据缓存到redis中
            ObjectMapper mapper = new ObjectMapper();
            allCategory = mapper.writeValueAsString(categories);
            jedis.set(Constant.ALLCATEGORY_KEY, allCategory);
        }
        //关闭redis
        jedis.close();

        return allCategory;
    }
}
