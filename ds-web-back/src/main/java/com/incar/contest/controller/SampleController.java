package com.incar.contest.controller;

import com.incar.contest.bean.Point;
import com.incar.contest.service.SampleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

/**
 *
 * 1. [接口]统计数据总行数
 * 2. [接口]查询指定车辆指定日期的行驶总里程
 * 3. [接口]查询指定车辆指定时间段(不短于72小时)的轨迹
 *
 * Created by bzheng on 2020/2/29.
 */
@RestController
@RequestMapping(value = "/sample")
public class SampleController {

    @Autowired
    private SampleService sampleService;


    /**
     * 统计数据总行数
     * @return
     */
    @GetMapping("/getCount")
    public Long getCount() {
        return sampleService.getCount();
    }


    /**
     * 查询指定车辆指定日期的行驶总里程
     * @param deviceCode
     * @param startTime
     * @param endTime
     * @return
     */
    @GetMapping("/getTotalMileage")
    public Double getTotalMileage(@RequestParam(value = "deviceCode") String deviceCode,
                                        @RequestParam(value = "startTime") Date startTime,
                                        @RequestParam(value = "endTime") Date endTime) {
        return sampleService.getTotalMileage(deviceCode, startTime, endTime);
    }


    /**
     * 查询指定车辆指定时间段(不短于72小时)的轨迹
     * @param deviceCode
     * @param startTime
     * @param endTime
     * @return
     */
    @GetMapping("/queryTrack")
    public List<Point> queryTrack(@RequestParam(value = "deviceCode") String deviceCode,
                                  @RequestParam(value = "startTime") Date startTime,
                                  @RequestParam(value = "endTime") Date endTime) {
        return sampleService.queryTrack(deviceCode, startTime, endTime);
    }
}
