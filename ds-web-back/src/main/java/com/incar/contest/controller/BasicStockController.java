package com.incar.contest.controller;

import com.incar.contest.bean.BasicStock;
import com.incar.contest.service.BasicStockService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * 基础信息
 *
 * @author GuoKun
 * @version 1.0
 * @create_date 2020/2/28 16:34
 */
@RestController
@RequestMapping(value = "/basicStock")
@Api(value = "基础示例调用接口 Client Restful API ", description = "基础示例调用接口 Client Restful API ", protocols = "application/json")
public class BasicStockController {
    @Autowired
    private BasicStockService basicStockService;

    @RequestMapping(value = "/findAll",method = RequestMethod.GET)
    public List<BasicStock> findAll(){
        return basicStockService.findAll();
    }

}
