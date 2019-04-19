package com.itheima.travel.mapper;

import com.itheima.travel.domain.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * @author LloydLai
 * @version 1.0
 * @Description: TODO
 * @date Created in 2019/3/21 21:27
 * @Modified By: TODO
 */
public interface IUserMapper {
    @Select("select * from tab_user where username = #{username};")
    User findUserByUsername(String username);

    @Insert("insert into tab_user values(null, #{username}, #{password}, #{name}, #{birthday}, #{sex}, #{telephone}, #{email}, #{status}, #{code});")
    boolean saveUser(User user);

    @Select("select * from tab_user where code = #{code};")
    User findUserByCode(String code);

    @Update("update tab_user set username = #{username}, password = #{password}, name = #{name}, birthday = #{birthday}, sex = #{sex}, telephone = #{telephone}, email = #{email}, status = #{status} where uid = #{uid};")
    boolean updateUser(User user);
}
