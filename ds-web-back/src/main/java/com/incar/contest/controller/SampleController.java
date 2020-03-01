package com.incar.contest.controller;

import com.incar.contest.bean.DeviceInfo;
import com.incar.contest.bean.Pagination;
import com.incar.contest.bean.Point;
import com.incar.contest.service.SampleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * 1. [接口]统计数据总行数
 * 2. [接口]查询指定车辆指定日期的行驶总里程
 * 3. [接口]查询指定车辆指定时间段(不短于72小时)的轨迹
 * <p>
 * Created by bzheng on 2020/2/29.
 */
@RestController
@RequestMapping(value = "/api/contest/sample")
@Api(value = "车辆信息接口 Client Restful API ", description = "车辆信息接口 Client Restful API ", protocols = "application/json")
public class SampleController {

    @Autowired
    private SampleService sampleService;


    /**
     * 统计数据总行数
     *
     * @return
     */
    @GetMapping("/getCount")
    @ApiOperation(value = "统计数据总行数", notes = "统计数据总行数")
    @ApiResponse(code = 200, message = "统计数据总行数")
    public Long getCount() {
        return sampleService.getCount();
    }


    /**
     * 查询指定车辆指定日期的行驶总里程
     *
     * @param deviceCode
     * @param specified
     * @return
     */
    @GetMapping("/getTotalMileage")
    @ApiOperation(value = "查询指定车辆指定日期的行驶总里程", notes = "查询指定车辆指定日期的行驶总里程")
    @ApiResponse(code = 200, message = "查询指定车辆指定日期的行驶总里程")
    public DeviceInfo getTotalMileage(@ApiParam(value = "设备号", required = true)
                                  @RequestParam(value = "deviceCode") String deviceCode,
                                      @ApiParam(value = "指定日期", required = true)
                                  @RequestParam(value = "specified") Date specified) {
        return sampleService.getTotalMileage(deviceCode, specified);
    }


    /**
     * 查询指定车辆指定时间段(不短于72小时)的轨迹
     *
     * @param deviceCode
     * @param startTime
     * @param endTime
     * @return
     */
    @GetMapping("/queryTrack")
    @ApiOperation(value = "查询指定车辆指定时间段(不短于72小时)的轨迹", notes = "查询指定车辆指定时间段(不短于72小时)的轨迹")
    @ApiResponse(code = 200, message = "查询指定车辆指定时间段(不短于72小时)的轨迹")
    public List<Point> queryTrack(@ApiParam(value = "设备号", required = true)
                                      @RequestParam(value = "deviceCode") String deviceCode,
                                  @ApiParam(value = "采集开始时间", required = true)
                                      @RequestParam(value = "startTime") Date startTime,
                                  @ApiParam(value = "采集结束时间", required = true)
                                      @RequestParam(value = "endTime") Date endTime) {
        return sampleService.queryTrack(deviceCode, startTime, endTime);
    }


    @GetMapping("/findPageList/{pageNum}/{pageSize}")
    @ApiOperation(value = "分页查询车辆的行驶总里程之和", notes = "分页查询车辆的行驶总里程之和")
    @ApiResponse(code = 200, message = "分页查询车辆的行驶总里程之和")
    public Pagination<DeviceInfo> findPageList(@ApiParam(value = "设备号", required = false)
                                  @RequestParam(value = "deviceCode", required = false) String deviceCode,
                                               @ApiParam(value = "当前页数")
                                  @PathVariable(value = "pageNum") Integer pageNum,
                                               @ApiParam(value = "每页条数")
                                  @PathVariable(value = "pageSize") Integer pageSize) {

        return sampleService.findPageList(deviceCode, pageNum, pageSize);
    }




    @GetMapping("/totalMileage")
    @ApiOperation(value = "查询所有车辆的行驶总里程之和", notes = "查询所有车辆的行驶总里程之和")
    @ApiResponse(code = 200, message = "查询所有车辆的行驶总里程之和")
    public Double totalMileage() {
        return sampleService.getTotalMileage();
    }
}
