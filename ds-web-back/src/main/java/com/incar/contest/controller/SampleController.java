package com.incar.contest.controller;

import com.incar.contest.bean.Points;
import com.incar.contest.bean.Sample;
import com.incar.contest.service.SampleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

/**
 * Created by bzheng on 2020/2/29.
 */
@RestController
@RequestMapping(value = "/sample")
public class SampleController {

    @Autowired
    private SampleService sampleService;

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
        return sampleService.getCount();
    }

    @GetMapping("/getTotalMileage")
    public Double getTotalMileage(@RequestParam(value = "deviceCode") String deviceCode,
                                        @RequestParam(value = "startTime") Date startTime,
                                        @RequestParam(value = "endTime") Date endTime) {
        return sampleService.getTotalMileage(deviceCode, startTime, endTime);
    }

    @GetMapping("/queryTrack")
    public List<Points> queryTrack(@RequestParam(value = "deviceCode") String deviceCode,
                                   @RequestParam(value = "startTime") Date startTime,
                                   @RequestParam(value = "endTime") Date endTime) {
        return sampleService.queryTrack(deviceCode, startTime, endTime);
    }
}
