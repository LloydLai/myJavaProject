package com.itheima.travel.web.controller;


import com.itheima.travel.domain.ResultInfo;
import com.itheima.travel.service.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * @author LloydLai
 * @version 1.0
 * @Description: 处理显示首页内容类别的servlet
 * @date Created in 2019/3/21 15:32
 * @Modified By: TODO
 */
@Controller
@RequestMapping(value = "/category")
public class CategoryController {
    private final ICategoryService service;

    @Autowired
    public CategoryController(ICategoryService service) {
        this.service = service;
    }

    /**
     * 此方法查找全部分类内容
     *
     * @return info
     */
    @ResponseBody
    @RequestMapping(value = "/findAllCategory")
    private ResultInfo findAllCategory() {
        ResultInfo info = new ResultInfo(true);
        try {
            String categories = service.findAllCategory();
            info.setData(categories);
        } catch (Exception e) {
            e.printStackTrace();
            info.setFlag(false);
            info.setErrorMsg(e.getMessage());
        }
        return info;
    }
}
