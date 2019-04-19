package com.itheima;

import com.itheima.travel.mapper.IFavoriteMapper;
import com.itheima.travel.mapper.IRouteMapper;
import com.itheima.travel.mapper.IUserMapper;
import com.itheima.travel.domain.Favorite;
import com.itheima.travel.domain.User;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Map;

/**
 * @author LloydLai
 * @version 1.0
 * @Description: TODO
 * @date Created in 2019/4/14 22:49
 * @Modified By: TODO
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:SpringMVC.xml"})
public class TestAccount {
    private IUserMapper userMapper;
    private IRouteMapper routeMapper;
    private IFavoriteMapper favoriteMapper;

    @Autowired
    private SqlSessionFactoryBean sqlSessionFactoryBean;

    @Before
    public void begin() {
        try {
            SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBean.getObject();
            if (sqlSessionFactory != null) {
                SqlSession sqlSession = sqlSessionFactory.openSession();
                userMapper = sqlSession.getMapper(IUserMapper.class);
                routeMapper = sqlSession.getMapper(IRouteMapper.class);
                favoriteMapper = sqlSession.getMapper(IFavoriteMapper.class);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testFindUserByUsername() {
        User user = userMapper.findUserByUsername("jay_123");
        System.out.println(user);
    }

    @Test
    public void testFindRouteByRid() {
        Map<String, Object> map = routeMapper.findRouteByRid(1);
        System.out.println(map);
    }

    @Test
    public void testFindUserByCode() {
        User user = userMapper.findUserByCode("f2f73da095d945f6825270f37528a896");
        System.out.println(user);
    }

    @Test
    public void testFindFavorite() {
        User user = new User();
        user.setUid(1);
        Favorite favorite = favoriteMapper.findFavorite(147, user);
        System.out.println(favorite);
    }

}
