package com.incar.contest.service;

import com.incar.contest.bean.Point;
import com.incar.contest.bean.Sample;
import com.incar.contest.elastic.Elasticsearch;
import com.incar.contest.share.Constant;
import com.incar.contest.util.TrajectoryCompressionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
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
            total = elasticsearch.getTotal(Constant.SAMPLE_INDEX, Constant.SAMPLE_TYPE, null);
        } catch (Exception e) {
            throw new RuntimeException("获取总条数失败");
        }

        return total;
    }

    /**
     * 查询指定车辆指定日期的行驶总里程
     * @param deviceCode
     * @param startTime
     * @param endTime
     * @return
     */
    public Double getTotalMileage(String deviceCode, Date startTime, Date endTime) {
        Double total = 0D;
        Map<String, Object> map = new HashMap<>();
        map.put("deviceCode", deviceCode);
        String timeType = "collectTime";
        List<Sample> data = elasticsearch.getData(Constant.SAMPLE_INDEX, Constant.SAMPLE_TYPE, map, timeType, startTime, endTime, Sample.class);
        if (!CollectionUtils.isEmpty(data)) {
            total = data.stream().filter(sample -> Objects.nonNull(sample) && Objects.nonNull(sample.getDistance())).mapToDouble(Sample::getDistance).sum();
        }
        return total;
    }

    public List<Point> queryTrack(String deviceCode, Date startTime, Date endTime) {
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
            return TrajectoryCompressionUtil.TrajectoryOptimize(points, dMax);
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
    public List<Sample> findPageList(String deviceCode, Integer pageNum, Integer pageSize) {

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

        return elasticsearch.getDataByPage(Constant.SAMPLE_INDEX, Constant.SAMPLE_TYPE, map, Sample.class, pageNum, pageSize);
    }
}
