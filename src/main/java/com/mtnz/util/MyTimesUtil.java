package com.mtnz.util;

import org.apache.commons.collections.map.HashedMap;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class MyTimesUtil {


    /**
     *根据时间范围获得月份集
     * @return
     */
    public static List<String> getRangeSet(String beginDate, String endDate){
        /*      Date1.after(Date2),当Date1大于Date2时，返回TRUE，当小于等于时，返回false；
          Date1.before(Date2)，当Date1小于Date2时，返回TRUE，当大于等于时，返回false；
          如果业务数据存在相等的时候，而且相等时也需要做相应的业务判断或处理时，你需要使用：！Date1.after(Date2);*/
        List<String> rangeSet =null;
        SimpleDateFormat sdf = null;
        Date begin_date = null;
        Date end_date = null;
        rangeSet = new java.util.ArrayList<String>();
        sdf = new SimpleDateFormat("yyyy-MM");
        try {
            begin_date = sdf.parse(beginDate);//定义起始日期
            end_date = sdf.parse(endDate);//定义结束日期
        } catch (ParseException e) {
            System.out.println("时间转化异常，请检查你的时间格式是否为yyyy-MM或yyyy-MM-dd");
        }
        Calendar dd = Calendar.getInstance();//定义日期实例
        dd.setTime(begin_date);//设置日期起始时间
        while(!dd.getTime().after(end_date)){//判断是否到结束日期
            rangeSet.add(sdf.format(dd.getTime()));
            dd.add(Calendar.MONTH, 1);//进行当前日期月份加1
        }
        return rangeSet;
    }

    /**
     * 获取某月的开始时间
     * @param year
     * @param month
     * @return
     */
    public static Date getBeginTime(int year, int month) {
        YearMonth yearMonth = YearMonth.of(year, month);
        LocalDate localDate = yearMonth.atDay(1);
        LocalDateTime startOfDay = localDate.atStartOfDay();
        ZonedDateTime zonedDateTime = startOfDay.atZone(ZoneId.of("Asia/Shanghai"));

        return Date.from(zonedDateTime.toInstant());
    }

    /**
     * 获取某月的结束时间
     * @param year
     * @param month
     * @return
     */
    public static Date getEndTime(int year, int month) {
        YearMonth yearMonth = YearMonth.of(year, month);
        LocalDate endOfMonth = yearMonth.atEndOfMonth();
        LocalDateTime localDateTime = endOfMonth.atTime(23, 59, 59, 999);
        ZonedDateTime zonedDateTime = localDateTime.atZone(ZoneId.of("Asia/Shanghai"));
        return Date.from(zonedDateTime.toInstant());
    }

    /**
     * 获取当天的开始时间
     * @return
     */
    public static Date getStartTime() {
        Calendar todayStart = Calendar.getInstance();
        todayStart.set(Calendar.HOUR, 0);
        todayStart.set(Calendar.MINUTE, 0);
        todayStart.set(Calendar.SECOND, 0);
        todayStart.set(Calendar.MILLISECOND, 0);
        return todayStart.getTime();
    }

    /**
     * 获取当天的结束时间
     * @return
     */
    public static Date getEndTime() {
        Calendar todayEnd = Calendar.getInstance();
        todayEnd.set(Calendar.HOUR, 23);
        todayEnd.set(Calendar.MINUTE, 59);
        todayEnd.set(Calendar.SECOND, 59);
        todayEnd.set(Calendar.MILLISECOND, 999);
        return todayEnd.getTime();
    }


    /**
     * 获得最近七天的开始时间和结束时间
     * @return
     */
    public static Map getDaySevenRange(){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Map condition=new HashedMap();
        Calendar calendar = Calendar.getInstance();
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
        calendar.set(Calendar.HOUR_OF_DAY,24);
        condition.put("endDate",df.format(calendar.getTime()));
        calendar.set(Calendar.HOUR_OF_DAY,-168);
        condition.put("startDate",df.format(calendar.getTime()));
        return condition;
    }


    /**
     * 获得近一月的开始时间和结束时间
     * @return
     */
    public static Map getDayTRange(){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Map condition=new HashedMap();
        Calendar calendar = Calendar.getInstance();
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
        calendar.set(Calendar.HOUR_OF_DAY,24);
        condition.put("endDate",df.format(calendar.getTime()));
        calendar.set(Calendar.HOUR_OF_DAY,-720);
        condition.put("startDate",df.format(calendar.getTime()));
        return condition;
    }
}
