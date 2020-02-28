package com.incar.contest.util;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * ZonedDateTime Util
 *
 * @author Kong, created on 2020-02-27T11:59.
 * @since 1.0.0-SNAPSHOT
 */
public class ZonedDateTimeUtil {

    public static Date getDate(Date date,Integer num){
        ZoneId defaultZoneId = ZoneId.systemDefault();
        Instant instant = date.toInstant();
        ZonedDateTime zonedDateTime = instant.atZone(defaultZoneId);
        zonedDateTime = zonedDateTime.plusDays(-num) ;
        zonedDateTime = ZonedDateTime.of(zonedDateTime.getYear(), zonedDateTime.getMonthValue(),
                zonedDateTime.getDayOfMonth() , 0, 0,
                0, 0, defaultZoneId) ;
        return Date.from(zonedDateTime.toInstant()) ;
    }

    /**
     * 获取某天的后几天的起始日期
     * @return
     */
    public static Date getStartDate(Date date,Integer num){
        ZoneId defaultZoneId = ZoneId.systemDefault();
        Instant instant = date.toInstant();
        ZonedDateTime zonedDateTime = instant.atZone(defaultZoneId);
        zonedDateTime = zonedDateTime.plusDays(num) ;
        zonedDateTime = ZonedDateTime.of(zonedDateTime.getYear(), zonedDateTime.getMonthValue(),
                zonedDateTime.getDayOfMonth(), 0, 0,
                0, 0, defaultZoneId) ;
        return Date.from(zonedDateTime.toInstant()) ;
    }


    /**
     * 获取某天的后几天的结束日期
     * @return
     */
    public static Date getEndDate(Date date,Integer num){
        ZoneId defaultZoneId = ZoneId.systemDefault();
        Instant instant = date.toInstant();
        ZonedDateTime zonedDateTime = instant.atZone(defaultZoneId);
        zonedDateTime = zonedDateTime.plusDays(num) ;
        zonedDateTime = ZonedDateTime.of(zonedDateTime.getYear(), zonedDateTime.getMonthValue(),
                zonedDateTime.getDayOfMonth(), 23, 59,
                59, 0, defaultZoneId) ;
        return Date.from(zonedDateTime.toInstant()) ;
    }


    /**
     * 日期格式化
     * @param date
     * @return
     */
    public static String dateToStr(Date date) {
        ZoneId defaultZoneId = ZoneId.systemDefault();
        Instant instant = date.toInstant();
        ZonedDateTime zonedDateTime = instant.atZone(defaultZoneId);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd") ;
        return zonedDateTime.format(formatter);
    }

    /**
     * 日期格式化
     * @param date
     * @return
     */
    public static String getDateStryyyyMMdd(Date date) {
        ZoneId defaultZoneId = ZoneId.systemDefault();
        Instant instant = date.toInstant();
        ZonedDateTime zonedDateTime = instant.atZone(defaultZoneId);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd") ;
        return zonedDateTime.format(formatter);
    }

    /**
     * 功能描述：格式化输出日期
     * @param date 日期
     * @return 返回字符型日期
     */
    public static String format(Date date) {
        ZoneId defaultZoneId = ZoneId.systemDefault();
        Instant instant = date.toInstant();
        ZonedDateTime zonedDateTime = instant.atZone(defaultZoneId);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss") ;
        return zonedDateTime.format(formatter);
    }

}
