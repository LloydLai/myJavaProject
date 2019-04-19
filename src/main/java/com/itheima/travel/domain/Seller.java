package com.itheima.travel.domain;

import lombok.Data;

import java.io.Serializable;

/**
 * 商家实体类
 */
@Data
public class Seller implements Serializable {
    //商家id
    private Integer sid;
    //商家名称
    private String sname;
    //商家电话
    private String consphone;
    //商家地址
    private String address;
}
