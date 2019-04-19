package com.itheima.travel.domain;

import lombok.Data;

import java.io.Serializable;

/**
 * 旅游线路图片实体类
 */
@Data
public class RouteImg implements Serializable {
    //商品图片id
    private Integer rgid;
    //旅游商品id
    private Integer rid;
    //详情商品大图
    private String bigPic;
    //详情商品小图
    private String smallPic;
}
