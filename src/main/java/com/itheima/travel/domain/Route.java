package com.itheima.travel.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 旅游线路商品实体类
 */
@Data
public class Route implements Serializable {

    //线路id，必输
    private Integer rid;
    //线路名称，必输
    private String rname;
    //价格，必输
    private double price;
    //线路介绍
    private String routeIntroduce;
    //是否上架，必输，0代表没有上架，1代表是上架
    private String rflag;
    //上架时间
    private String rdate;
    //是否主题旅游，必输，0代表不是，1代表是
    private String isThemeTour;
    //收藏数量
    private Integer count;
    //所属分类，必输
    private Integer cid;
    //缩略图
    private String rimage;
    //所属商家
    private Integer sid;
    //抓取数据的来源id
    private String sourceId;

    //所属分类
    private Category category;
    //所属商家
    private Seller seller;
    //商品详情图片列表
    private List<RouteImg> routeImgList;
}
