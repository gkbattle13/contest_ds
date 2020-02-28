package com.incar.contest_ds.controller;

import com.incar.contest_ds.bean.BasicStock;
import com.incar.contest_ds.service.BasicStockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
public class BasicStockController {
    @Autowired
    private BasicStockService basicStockService;

    @RequestMapping(value = "/findAll",method = RequestMethod.GET)
    public List<BasicStock> findAll(){
        return basicStockService.findAll();
    }
}
