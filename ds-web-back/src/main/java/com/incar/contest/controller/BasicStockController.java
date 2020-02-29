package com.incar.contest.controller;

import com.incar.contest.bean.BasicStock;
import com.incar.contest.service.BasicStockService;
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
public class BasicStockController {
    @Autowired
    private BasicStockService basicStockService;

    @RequestMapping(value = "/findAll",method = RequestMethod.GET)
    public List<BasicStock> findAll(){
        return basicStockService.findAll();
    }

    /**
     * 4. [接口]统计数据总行数
     * 5. [接口]查询指定车辆指定日期的行驶总里程
     * 6. [接口]查询指定车辆指定时间段(不短于72小时)的轨迹
     * 7. [界面]展示统计数据总行数
     * 8. [界面]展示指定车辆指定日期的行驶里程
     * 9. [界面]展示指定车辆指定时间段(不短于72小时)的轨迹
     */
    @GetMapping("/getCount")
    public Long getCount() {
        return basicStockService.getCount();
    }

    @GetMapping("/findMileageByVin")
    public Double findTotalMileageByVin(@RequestParam(value = "vin") String vin,
                                 @RequestParam(value = "startTime") Date startTime,
                                 @RequestParam(value = "endTime") Date endTime) {
        return basicStockService.findTotalMileageByVin(vin, startTime, endTime);
    }
}
