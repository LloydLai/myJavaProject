package com.itheima.travel.domain;

import lombok.Data;

import java.io.Serializable;

/**
 * 分类实体类
 */
@Data
public class Category implements Serializable {

    //分类id
    private Integer cid;
    //分类名称
    private String cname;
}
