package com.itheima.travel.domain;

import lombok.Data;

import java.io.Serializable;

/**
 * 收藏实体类
 */
@Data
public class Favorite implements Serializable {
    //旅游线路对象
    private Route route;
    //收藏时间
    private String date;
    //所属用户
    private User user;

    private Integer rid;


}
