package com.jye.parkingchargesystem.charge;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by jye on 2017/2/9.
 */

public class DateUtils {

    /**
     * 获取相差的小时数(向上取整)
     *
     * @param entryTime 进行比较的时间【yyyy.MM.dd HH:mm:ss】
     * @return
     */
    public static int getHoursOfDifference(String entryTime) throws ParseException {
        DateFormat df = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
        //进场日期
        Date entryDate = df.parse(entryTime);
        //当前日期
        Date currentDate = new Date();

        System.out.println("进场时间: " + entryTime);
        System.out.println("当前时间: " + df.format(currentDate));

        //停车时间,单位毫秒
        long diff = currentDate.getTime() - entryDate.getTime();

        long nd = 1000 * 24 * 60 * 60;// 一天的毫秒数
        long nh = 1000 * 60 * 60;// 一小时的毫秒数
        long nm = 1000 * 60;// 一分钟的毫秒数
        long ns = 1000;// 一秒钟的毫秒数

        long days = diff / nd;// 计算差多少天
        long hours = diff % nd / nh + days * 24 - days * 24;// 计算差多少小时
        long minutes = diff % nd % nh / nm + days * 24 * 60 - days * 24 * 60;// 计算差多少分钟
        long seconds = diff % nd % nh % nm / ns;// 计算差多少秒

        // 输出结果
        System.out.println("进场时间与当前时间相差：" + days + "天" + hours + "小时"
                + minutes + "分钟" + seconds + "秒。");

        if (seconds >= 0 || minutes >= 0) {
            hours++;
        }

        int totalHours = (int) (hours + days * 24);
        System.out.println("已停总小时数: " + totalHours);
        return totalHours;
    }

    /**
     * 获取当前时间
     *
     * @return
     */
    public static String getCurrentTime() {
        DateFormat df = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
        return df.format(System.currentTimeMillis());
    }
}
