package com.incar.contest.service;

import com.incar.contest.bean.DeviceInfo;
import com.incar.contest.bean.Point;
import com.incar.contest.bean.Sample;
import com.incar.contest.elastic.Elasticsearch;
import com.incar.contest.share.Constant;
import com.incar.contest.util.GPSUtil;
import com.incar.contest.util.TrajectoryCompressionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StopWatch;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by bzheng on 2020/2/29.
 */
@Service
@Slf4j
public class SampleService {

    @Autowired
    private Elasticsearch elasticsearch;

    /**
     * 统计数据总行数
     * @return
     */
    public Long getCount() {
        Long total;
        try {
            total = elasticsearch.getTotal(Constant.SAMPLE_INDEX, Constant.SAMPLE_TYPE);
        } catch (Exception e) {
            throw new RuntimeException("获取总条数失败");
        }

        return total;
    }

    /**
     * 查询指定车辆指定日期的行驶总里程
     * @param deviceCode
     * @param specified 指定日期
     * @return
     */
    public DeviceInfo getTotalMileage(String deviceCode, Date specified) {
        if (StringUtils.isEmpty(deviceCode)) {
            throw new RuntimeException("deviceCode is null!!!");
        }
        double mileage = elasticsearch.getDataByGroup(Constant.SAMPLE_INDEX, Constant.SAMPLE_TYPE, deviceCode, specified);
        return new DeviceInfo(deviceCode, mileage);
    }

    /**
     * 查询车辆指定时间段(不短于72小时)的轨迹
     * @param deviceCode
     * @param startTime
     * @param endTime
     * @return
     */
    public List<Point> queryTrack(String deviceCode, Date startTime, Date endTime) {
        if (StringUtils.isEmpty(deviceCode)) {
            throw new RuntimeException("please choose deviceCode!!!");
        }
        Map<String, Object> map = new HashMap<>();
        map.put("deviceCode", deviceCode);
        String timeType = "collectTime";
        List<Sample> data = elasticsearch.getData(Constant.SAMPLE_INDEX, Constant.SAMPLE_TYPE, map, timeType, startTime, endTime, Sample.class);
        if (!CollectionUtils.isEmpty(data)) {
            List<Point> points = data.stream().map(sample -> {
                String longitudeStr = sample.getLongitude();
                String latitudeStr = sample.getLatitude();
                double longitude = 0D;
                double latitude = 0D;
                if (!StringUtils.isEmpty(longitudeStr) && !StringUtils.isEmpty(latitudeStr)) {
                    longitude = Double.parseDouble(longitudeStr.replace("E", ""));
                    latitude = Double.valueOf(latitudeStr.replace("N", ""));
                }
                return new Point(sample.getCollectTime(), longitude, latitude);
            }).collect(Collectors.toList());

            // 最大误差距离
            double dMax = 20d;

            // 压缩经纬度
            points = TrajectoryCompressionUtil.TrajectoryOptimize(points, dMax);

            // GPS坐标转换
            StopWatch watch = new StopWatch();
            watch.start();
            points.forEach(GPSUtil::gps84_To_Gcj02_03);
            watch.stop();
//            logger.info("StopWatch GPS坐标转换完成，转换数据量为：{}， 耗时：{} s",points.size(), watch.getTotalTimeSeconds());
            return points;
        }

        return Collections.emptyList();
    }


    /**
     * 分页查询车辆的行驶总里程之和
     * @param deviceCode
     * @param pageNum
     * @param pageSize
     * @return
     */
    public List<DeviceInfo> findPageList(String deviceCode, Integer pageNum, Integer pageSize) {

        if(StringUtils.isEmpty(pageNum)){
            pageNum = 1;
        }
        if(StringUtils.isEmpty(pageSize)){
            pageSize = 10;
        }
        Map<String, Object> map = new HashMap<>();
        if (!StringUtils.isEmpty(deviceCode)) {
            map.put("deviceCode", deviceCode);
        }

        return elasticsearch.getDataByPage(Constant.MILEAGE_INDEX, Constant.MILEAGE_TYPE, map, DeviceInfo.class, pageNum, pageSize);
    }
}
