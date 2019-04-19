package com.itheima.travel.domain;

import lombok.Data;

import java.io.Serializable;

/**
 * 用于封装后端返回前端数据对象
 */
@Data
public class ResultInfo implements Serializable {
    //后端返回结果正常为true，发生异常返回false
    private Boolean flag;
    //后端返回结果数据对象
    private Object data;
    //发生异常的错误消息
    private String errorMsg;

    public ResultInfo(boolean flag) {
        this.flag = flag;
    }

    public ResultInfo(boolean flag, String errorMsg) {
        this.flag = flag;
        this.errorMsg = errorMsg;
    }
}
