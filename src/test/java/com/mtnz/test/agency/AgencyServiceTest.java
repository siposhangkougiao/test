package com.mtnz.test.agency;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.lang.annotation.Target;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/*
    Created by xxj on 2018\3\27 0027.  
 */

@RunWith(SpringJUnit4ClassRunner.class)
public class AgencyServiceTest {

    public static Map<String, String> dealMonthDayHour(int month, int day, int hour) {

        Map<String, String> month_day_hour = new HashMap<String, String>();

        month = month % 12 + 1;// get the real month
        month_day_hour.put("month", dealZeroToNine(month));
        month_day_hour.put("day", dealZeroToNine(day));
        month_day_hour.put("hour", dealZeroToNine(hour));

        return month_day_hour;
    }

    public static String dealZeroToNine(int num) {
        if (num >= 1 && num <= 9) {
            return "0" + num;
        }
        return "" + num;
    }

    //
    //
    //
    //

    @Test
    @Ignore
    public void showYearMonthDayHour() {
        Calendar calendar = Calendar.getInstance();
        String year = String.valueOf(calendar.get(Calendar.YEAR));
        Map<String, String> month_day_hour = AgencyServiceTest.dealMonthDayHour(calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), calendar.get(Calendar.HOUR_OF_DAY));
        String month = month_day_hour.get("month");
        String day = month_day_hour.get("day");
        String hour = month_day_hour.get("hour");
        System.out.println(year + "\t" + month + "\t" + day + "\t " + hour);
    }

}
