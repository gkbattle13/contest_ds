package com.incar.contest.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.util.*;

/**
 * 日期帮助类
 * 1、所有涉及到时区逻辑,日期转换均转换成 ZonedDateTime 运算然后再 转回Date
 * 2、所有涉及到时区的参数,最好使用ZonedId.of()里面传入时区英文,原因如下:
 *    在初始化ZonedDateTime时候，如果传入的时区参数ZoneId.of()中参数为
 *    @see ZoneId#SHORT_IDS 中非偏移量
 *    和
 *    偏移量(+08:00) 时候会导致不一样的结果
 *    前者为带时区处理了夏令时,后者仅仅为时区偏移量不考虑夏令时
 *    详情参见:
 *    @see ZoneId#of(String)
 *
 *
 */
public class DateUtil {

    public final static String DATE_FORMAT_DAY="yyyy-MM-dd";
    public final static String DATE_FORMAT_SECOND="yyyy-MM-dd HH:mm:ss";

    /**
     * 将日期转换为字符串
     * @param date
     * @param format
     * @param zoneId
     * @return
     */
    public static String dateToString(Date date,String format,ZoneId zoneId){
        if(date==null||format==null||zoneId==null){
            return null;
        }
        return DateTimeFormatter.ofPattern(format).withZone(zoneId).format(date.toInstant());
    }


    /**
     * 将日期字符串转换为 时间类型
     * @param dateStr
     * @param format
     * @param zoneId
     * @return
     */
    public static Date stringToDate(String dateStr,String format,ZoneId zoneId){
        if(dateStr==null||format==null||zoneId==null){
            return null;
        }
        return Date.from(Instant.from(DateTimeFormatter.ofPattern(format).withZone(zoneId).parse(dateStr)));
    }




    /**
     * 获取最近在当前日期之前的最后一个日期单位
     * @param date
     * @param unit 支持 ChronoUnit.MILLIS,ChronoUnit.SECONDS,ChronoUnit.MINUTES,ChronoUnit.HOURS,ChronoUnit.DAYS,ChronoUnit.MONTHS,ChronoUnit.YEARS
     * @param zoneId 时区
     * @return
     */
    public static Date getFloorDate(Date date,ChronoUnit unit,ZoneId zoneId){
        if(date==null){
            return null;
        }
        ChronoUnit[] units=new ChronoUnit[]{ChronoUnit.MILLIS,ChronoUnit.SECONDS,ChronoUnit.MINUTES,ChronoUnit.HOURS,ChronoUnit.DAYS,ChronoUnit.MONTHS,ChronoUnit.YEARS};
        if(!Arrays.stream(units).anyMatch(e->e==unit)){
            throw new RuntimeException("[DateUtil.getFloorDate],ChronoUnit["+unit.toString()+"] Not Support!");
        }
        ZonedDateTime zdt=ZonedDateTime.ofInstant(date.toInstant(),zoneId);
        if(unit.ordinal()<=ChronoUnit.DAYS.ordinal()){
            zdt=zdt.truncatedTo(unit);

        }else{
            zdt=zdt.truncatedTo(ChronoUnit.DAYS);
            switch (unit){
                case MONTHS:{
                    zdt=zdt.withDayOfMonth(1);
                    break;
                }
                case YEARS:{
                    zdt=zdt.withDayOfMonth(1);
                    zdt=zdt.withMonth(1);
                    break;
                }
            }
        }
        return Date.from(zdt.toInstant());
    }



    /**
     * 获取最近在当前日期之后的第一个日期单位
     * @param date
     * @param unit 支持 ChronoUnit.MILLIS,ChronoUnit.SECONDS,ChronoUnit.MINUTES,ChronoUnit.HOURS,ChronoUnit.DAYS,ChronoUnit.MONTHS,ChronoUnit.YEARS
     * @param zoneId 时区
     * @return
     */
    public static Date getCeilDate(Date date,ChronoUnit unit,ZoneId zoneId){
        if(date==null){
            return null;
        }
        ChronoUnit[] units=new ChronoUnit[]{ChronoUnit.MILLIS,ChronoUnit.SECONDS,ChronoUnit.MINUTES,ChronoUnit.HOURS,ChronoUnit.DAYS,ChronoUnit.MONTHS,ChronoUnit.YEARS};
        if(!Arrays.stream(units).anyMatch(e->e==unit)){
            throw new RuntimeException("[DateUtil.getCeilDate],ChronoUnit["+unit.toString()+"] Not Support!");
        }
        ZonedDateTime zdt=ZonedDateTime.ofInstant(date.toInstant(),zoneId);
        if(unit.ordinal()<=ChronoUnit.DAYS.ordinal()){
            zdt=zdt.truncatedTo(unit);
            zdt=zdt.plus(1,unit);

        }else{
            zdt=zdt.truncatedTo(ChronoUnit.DAYS);
            switch (unit){
                case MONTHS:{
                    zdt=zdt.withDayOfMonth(1);
                    zdt=zdt.plusMonths(1);
                    break;
                }
                case YEARS:{
                    zdt=zdt.withDayOfMonth(1);
                    zdt=zdt.withMonth(1);
                    zdt=zdt.plusYears(1);
                    break;
                }
            }
        }
        return Date.from(zdt.toInstant());
    }


    /**
     * 获取开始时间结束时间按照 日期单位 形成多个日期区间
     * 第一个区间开始时间为传入开始时间
     * 最后一个区间结束时间为传入结束时间
     * @param startDate
     * @param endDate
     * @param unit 支持 ChronoUnit.DAYS,ChronoUnit.WEEKS,ChronoUnit.MONTHS
     * @param zoneId 时区
     * @return 每一个数组第一个为开始时间,第二个为结束时间;开始时间为当天0.0.0,结束时间为当天23.59.59
     */
    public static List<Date[]> rangeDate(Date startDate, Date endDate, ChronoUnit unit,ZoneId zoneId){
        List<Date[]> returnList=new ArrayList<>();
        ZonedDateTime zdt1= ZonedDateTime.ofInstant(startDate.toInstant(),zoneId);
        ZonedDateTime zdt2= ZonedDateTime.ofInstant(endDate.toInstant(),zoneId);
        switch (unit){
            case DAYS:{
                ZonedDateTime start= zdt1.with(ChronoField.SECOND_OF_DAY,0);
                ZonedDateTime end= zdt1.with(ChronoField.SECOND_OF_DAY, ChronoUnit.DAYS.getDuration().getSeconds()-1);
                while(true){
                    returnList.add(new Date[]{Date.from(start.toInstant()),Date.from(end.toInstant())});
                    if(!zdt2.isBefore(start)&&!zdt2.isAfter(end)){
                        break;
                    }else{
                        start=start.plusDays(1);
                        end=end.plusDays(1);
                    }
                }

                break;
            }
            case WEEKS:{
                int dayOfWeek=zdt1.get(ChronoField.DAY_OF_WEEK);
                ZonedDateTime start= zdt1.plusDays(1-dayOfWeek).with(ChronoField.SECOND_OF_DAY,0);
                ZonedDateTime end= zdt1.plusDays(7-dayOfWeek).with(ChronoField.SECOND_OF_DAY, ChronoUnit.DAYS.getDuration().getSeconds()-1);
                while(true){
                    returnList.add(new Date[]{Date.from(start.toInstant()),Date.from(end.toInstant())});
                    if(!zdt2.isBefore(start)&&!zdt2.isAfter(end)){
                        break;
                    }else{
                        start=start.plusWeeks(1);
                        end=end.plusWeeks(1);
                    }
                }
                if(returnList.size()>0){
                    Date[] firstEle=returnList.get(0);
                    Date[] lastEle=returnList.get(returnList.size()-1);
                    firstEle[0]=Date.from(zdt1.with(ChronoField.SECOND_OF_DAY,0).toInstant());
                    lastEle[1]=Date.from(zdt2.with(ChronoField.SECOND_OF_DAY,0).toInstant());
                }
                break;
            }
            case MONTHS:{
                ZonedDateTime temp=zdt1;
                while(true) {
                    int dayOfMonth = temp.get(ChronoField.DAY_OF_MONTH);
                    int max = temp.getMonth().maxLength();
                    ZonedDateTime start = temp.plusDays(1 - dayOfMonth).with(ChronoField.SECOND_OF_DAY, 0);
                    ZonedDateTime end = temp.plusDays(max - dayOfMonth).with(ChronoField.SECOND_OF_DAY, ChronoUnit.DAYS.getDuration().getSeconds() - 1);
                    returnList.add(new Date[]{Date.from(start.toInstant()),Date.from(end.toInstant())});
                    if(!zdt2.isBefore(start)&&!zdt2.isAfter(end)){
                        break;
                    }else{
                        temp=temp.plusMonths(1);
                    }
                }
                if(returnList.size()>0){
                    Date[] firstEle=returnList.get(0);
                    Date[] lastEle=returnList.get(returnList.size()-1);
                    firstEle[0]=Date.from(zdt1.with(ChronoField.SECOND_OF_DAY,0).toInstant());
                    lastEle[1]=Date.from(zdt2.with(ChronoField.SECOND_OF_DAY,0).toInstant());
                }
                break;
            }
            default:{
                throw new RuntimeException("[DateUtil.rangeDate],ChronoUnit["+unit.toString()+"] Not Support!");
            }
        }
        return returnList;
    }

    public static long getDiff(Date d1,Date d2,ChronoUnit unit){
        return getDiff(d1, d2, unit,true);
    }

    /**
     * 计算两个时间相差多少日期单位(不足一个日期单位的的按一个日期单位算)
     * @param d1 开始时间
     * @param d2 结束时间
     * @param unit 支持ChronoUnit.MILLIS,ChronoUnit.SECONDS,ChronoUnit.MINUTES,ChronoUnit.HOURS,ChronoUnit.DAYS
     * @param up 如果存在小数位,是向上取整还是向下取整;true代表向上;false代表向下
     * @return 相差日期单位数
     */
    public static long getDiff(Date d1,Date d2,ChronoUnit unit,boolean up)
    {
        ChronoUnit[] units=new ChronoUnit[]{ChronoUnit.MILLIS,ChronoUnit.SECONDS,ChronoUnit.MINUTES,ChronoUnit.HOURS,ChronoUnit.DAYS, ChronoUnit.MONTHS};
        if(!Arrays.stream(units).anyMatch(e->e==unit)){
            throw new RuntimeException("[DateUtil.getDiff],ChronoUnit["+unit.toString()+"] Not Support!");
        }
        long diff;
        switch (unit){
            case DAYS:{
                diff=ChronoUnit.DAYS.getDuration().toMillis();
                break;
            }
            case HOURS:{
                diff=ChronoUnit.HOURS.getDuration().toMillis();
                break;
            }
            case MINUTES:{
                diff=ChronoUnit.MINUTES.getDuration().toMillis();
                break;
            }
            case SECONDS:{
                diff=ChronoUnit.SECONDS.getDuration().toMillis();
                break;
            }
            case MILLIS:{
                diff=ChronoUnit.MILLIS.getDuration().toMillis();
                break;
            }
            case MONTHS:{
                diff = ChronoUnit.MONTHS.getDuration().toMillis();
                break;
            }
            default:{
                throw new RuntimeException("[DateUtil.getDiff],ChronoUnit["+unit.toString()+"] Not Support!");
            }
        }
        long begin = d1.getTime();
        long end = d2.getTime();
        double res= (end-begin)/((double)diff);
        if(up){
            return (int)Math.ceil(res);
        }else{
            return (int)Math.floor(res);
        }
    }


    /**
     * 会改变参数值
     * 格式化日期参数开始日期和结束日期
     * 格式规则为:
     *      开始日期去掉时分秒
     *      结束日期设置为当天 23:59:59
     * @param startDate
     * @param endDate
     * @param zoneId
     */
    public static void formatDateParam(Date startDate,Date endDate,ZoneId zoneId){
        if(startDate!=null){
            startDate.setTime(getFloorDate(startDate,ChronoUnit.DAYS,zoneId).getTime());
        }
        if(endDate!=null){
            Date tempDate= getCeilDate(endDate,ChronoUnit.DAYS,zoneId);
            Calendar endC=Calendar.getInstance();
            endC.setTime(tempDate);
            endC.add(Calendar.SECOND,-1);
            endDate.setTime(endC.getTimeInMillis());
        }
    }

    /**
     * 判断两个日期是否相等
     * 对比顺序
     * 年、月、日、时、分、秒、毫秒
     * @param d1
     * @param d2
     * @param field 对比的最小日期单位 支持 ChronoField.YEAR,ChronoField.MONTH_OF_YEAR,ChronoField.DAY_OF_MONTH,ChronoField.HOUR_OF_DAY,ChronoField.MINUTE_OF_HOUR,ChronoField.SECOND_OF_MINUTE,ChronoField.MILLI_OF_SECOND
     * @return
     */
    public static boolean isEqual(Date d1,Date d2,ChronoField field){
        ChronoField[] fields=new ChronoField[]{ChronoField.YEAR,ChronoField.MONTH_OF_YEAR,ChronoField.DAY_OF_MONTH,ChronoField.HOUR_OF_DAY,ChronoField.MINUTE_OF_HOUR,ChronoField.SECOND_OF_MINUTE,ChronoField.MILLI_OF_SECOND};
        if(!Arrays.stream(fields).anyMatch(e->e==field)){
            throw new RuntimeException("[DateUtil.isEqual],ChronoField["+field.toString()+"] Not Support!");
        }
        ZoneId zoneId=ZoneId.systemDefault();
        ZonedDateTime zdt1=ZonedDateTime.ofInstant(d1.toInstant(),zoneId);
        ZonedDateTime zdt2=ZonedDateTime.ofInstant(d2.toInstant(),zoneId);
        for (ChronoField curField : fields) {
            int curVal1=zdt1.get(curField);
            int curVal2=zdt2.get(curField);
            if(curVal1!=curVal2){
                return false;
            }
            if(curField==field){
                break;
            }
        }
        return true;
    }

    /**
     * 获取几天前/后的时间
     * @param n n > 0 后，n < 0 前
     * @return
     */
    public static Date getPlusDay(int n) {
        // 获取n天后的数据
        return getPlusDay(Calendar.DATE, n);
    }

    /**
     * 获取几天前/后的时间
     * @param n n > 0 后，n < 0 前
     * @return
     */
    public static Date getPlusDay(Date date, int n) {
        // 获取n天后的数据
        return getPlusDay(date, Calendar.DATE, n);
    }

    /**
     * 获取 n 个时间单位前后的时间
     * @param type
     * @see Calendar#DATE  {@link Calendar#MONTH,Calendar#SECOND }
     * @param n
     * @return
     */
    public static Date getPlusDay(int type, int n) {
        return getPlusDay(null, type, n);
    }

    /**
     * 获取 n 个时间单位前后的时间
     * @param type
     * @see Calendar#DATE,Calendar#SECOND
     * @param n
     * @return
     */
    public static Date getPlusDay(Date date, int type, int n) {
        Calendar instance = Calendar.getInstance();
        if (Objects.nonNull(date)) {
            instance.setTime(date);
        }
        instance.add(type, n);
        // 获取n天后的数据
        return instance.getTime();
    }

    public static String formatDate(Date date, String format) {
        if (null != date) {
            DateFormat dateFormat = new SimpleDateFormat(format);
            return dateFormat.format(date);
        }
        return null;
    }

    /**
     * 获取本年的字符 或者 本月字符，主要处理结束日期是本月或者本年的
     * @param flag 年 Calendar.YEAR / 月 Calendar.MONTH
     *
     * @return
     */
    public static String getNow(int flag) {
        // 获取年
        Calendar cale = Calendar.getInstance();
        int year = cale.get(Calendar.YEAR);
        if (Calendar.YEAR == flag) {
            return year + "";
        }
        int month = cale.get(Calendar.MONTH) + 1;
        if (Calendar.MONTH == flag) {
            return year + "-" + month;
        }
        return null;
    }

    /**
     * 获取本月的开始时间
     *
     * @return
     */
    public static Date getBeginDayOfMonth() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        calendar.set(year, month - 1, 1);
        return getDayStartTime(calendar.getTime());
    }

    public static Date getBeginDayOfMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        calendar.set(year, month - 1, 1);
        return getDayStartTime(calendar.getTime());
    }

    /**
     * 获取本月的结束时间
     *
     * @return
     */
    public static Date getEndDayOfMonth() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        calendar.set(year, month - 1, 1);
        int day = calendar.getActualMaximum(5);
        calendar.set(year, month - 1, day);
        return getDayEndTime(calendar.getTime());
    }

    /**
     * 获取本月的结束时间
     *
     * @return
     */
    public static Date getEndDayOfMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        calendar.set(year, month - 1, 1);
        int day = calendar.getActualMaximum(5);
        calendar.set(year, month - 1, day);
        return getDayEndTime(calendar.getTime());
    }

    /**
     * 获取上月的开始时间
     *
     * @return
     */
    public static Date getBeginDayOfLastMonth() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        calendar.set(year, month - 2, 1);
        return getDayStartTime(calendar.getTime());
    }

    /**
     * 获取上月的结束时间
     *
     * @return
     */
    public static Date getEndDayOfLastMonth() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        calendar.set(year, month - 2, 1);
        int day = calendar.getActualMaximum(5);
        calendar.set(year, month - 2, day);
        return getDayEndTime(calendar.getTime());
    }

    /**
     * 获取本年的开始时间
     *
     * @return
     */
    public static Date getBeginDayOfYear() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        calendar.set(Calendar.YEAR, year);
        // cal.set
        calendar.set(Calendar.MONTH, Calendar.JANUARY);
        calendar.set(Calendar.DATE, 1);

        return getDayStartTime(calendar.getTime());
    }

    /**
     * 获取某年的开始时间
     *
     * @return
     */
    public static Date getBeginDayOfYear(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);
        calendar.set(Calendar.YEAR, year);
        // cal.set
        calendar.set(Calendar.MONTH, Calendar.JANUARY);
        calendar.set(Calendar.DATE, 1);

        return getDayStartTime(calendar.getTime());
    }

    /**
     * 获取本年的结束时间
     *
     * @return
     */
    public static Date getEndDayOfYear() {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, Calendar.DECEMBER);
        cal.set(Calendar.DATE, 31);
        return getDayEndTime(cal.getTime());
    }

    /**
     * 获取某年的结束时间
     *
     * @return
     */
    public static Date getEndDayOfYear(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int year = cal.get(Calendar.YEAR);
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, Calendar.DECEMBER);
        cal.set(Calendar.DATE, 31);
        return getDayEndTime(cal.getTime());
    }


    /**
     * 获取某个日期的开始时间
     *
     * @param d
     * @return
     */
    public static Timestamp getDayStartTime(Date d) {
        Calendar calendar = Calendar.getInstance();
        if (null != d) {
            calendar.setTime(d);
        }
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return new Timestamp(calendar.getTimeInMillis());
    }

    /**
     * 获取某个日期的结束时间
     *
     * @param d
     * @return
     */
    public static Timestamp getDayEndTime(Date d) {
        Calendar calendar = Calendar.getInstance();
        if (null != d) {
            calendar.setTime(d);
        }
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), 23, 59, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return new Timestamp(calendar.getTimeInMillis());
    }


    public static String formatDate(Date date) {
        return formatDate(date, DATE_FORMAT_SECOND);
    }

    public static String formatDate(String format) {
        return formatDate(new Date(), format);
    }

    public static String formatDate() {
        return formatDate(new Date(), DATE_FORMAT_SECOND);
    }

    public static void main(String [] args){
        ZoneId zoneId1= ZoneId.of("Asia/Shanghai");
        ZoneId zoneId2= ZoneId.of("+8");
        ZonedDateTime zdt1=LocalDateTime.of(2019,4,3,11,11).atZone(zoneId1);
        ZonedDateTime zdt2=LocalDateTime.of(2019,4,3,11,11).atZone(zoneId2);
        System.out.println(zdt1.toInstant().toEpochMilli());
        System.out.println(zdt2.toInstant().toEpochMilli());
        System.out.println(OffsetDateTime.now().getOffset());
        Date floorDate = getFloorDate(new Date(), ChronoUnit.DAYS, zoneId1);
        Date ceilDate = getCeilDate(new Date(), ChronoUnit.DAYS, zoneId1);
        Date plusDay = getPlusDay(floorDate, -1);
        System.out.println(floorDate + "\n" + ceilDate + "\n" + plusDay);
    }
}
