package com.incar.contest.service;

import com.incar.contest.elastic.Elasticsearch;
import com.incar.contest.share.Constant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

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
    public Double findTotalMileageByVin(String deviceCode, Date startTime, Date endTime) {
//        elasticsearch.getData();
        //INCAR10001033372
        return 100.45;
    }
}
