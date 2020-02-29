package com.incar.contest.config.converter;

import com.incar.contest.util.DateUtil;
import com.incar.contest.util.ZonedDateTimeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.util.StringUtils;

import java.util.Date;

/**
 * Created by bzheng on 2019/11/13.
 */
@Slf4j
public class DateConverter implements Converter<String, Date> {


    @Override
    public Date convert(String source) {
        if (StringUtils.isEmpty(source)) {
            return null;
        }

        // 判断字符的长度
        if (DateUtil.DATE_FORMAT_SECOND.length() == source.length()) {
            try {
                return ZonedDateTimeUtil.stringToDate(source, DateUtil.DATE_FORMAT_SECOND);
            } catch (Exception e) {
                log.error("日期转化失败：" + e.getMessage(), e);
            }
        }

        // 前端一般传毫秒数
        try {
            long t = Long.parseLong(source);
            return new Date(t);
        }catch (NumberFormatException e){
            log.error("日期转化失败：" + e.getMessage(), e);
        }

        return null;
    }

}
