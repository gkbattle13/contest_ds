package com.incar.contest.service;

import com.incar.contest.bean.Points;
import com.incar.contest.bean.Sample;
import com.incar.contest.elastic.Elasticsearch;
import com.incar.contest.share.Constant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

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
            log.info("获取总条数失败：" + e.getMessage(), e);
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

    public List<Points> queryTrack(String deviceCode, Date startTime, Date endTime) {
        Map<String, Object> map = new HashMap<>();
        map.put("deviceCode", deviceCode);
        String timeType = "collectTime";
        List<Sample> data = elasticsearch.getData(Constant.SAMPLE_INDEX, Constant.SAMPLE_TYPE, map, timeType, startTime, endTime, Sample.class);
        if (!CollectionUtils.isEmpty(data)) {
            List<Points> collect = data.stream().map(sample -> {
                Points points = new Points();
                points.setLatitude(sample.getLatitude());
                points.setLongitude(sample.getLongitude());
                return points;
            }).collect(Collectors.toList());

            return collect;
        }

        return Collections.emptyList();
    }
}
