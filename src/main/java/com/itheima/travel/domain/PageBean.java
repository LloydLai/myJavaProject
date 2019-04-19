package com.itheima.travel.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author LloydLai
 * @version 1.0
 * @Description: TODO
 * @date Created in 2019/3/26 16:51
 * @Modified By: TODO
 */
@Data
public class PageBean<T> implements Serializable {
    //Object数据集合
    private List<T> list;
    //总页数
    private Long totalPage;
    //当前页数
    private Long currentPage;
    //总数据条数
    private Long totalSize;
    //每页的数据条数
    private Integer pageSize;

    public void setTotalSize(Long totalSize) {
        this.totalSize = totalSize;

        totalPage = totalSize % pageSize == 0 ? totalSize / pageSize : totalSize / pageSize + 1;
    }
}
