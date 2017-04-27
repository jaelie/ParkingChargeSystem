package com.jye.parkingchargesystem.charge;

import com.jye.parkingchargesystem.charge.vo.NextBillingInfo;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.Calendar;
import java.util.Date;

/**
 * 抽象收费策略
 * Created by jye on 2017/2/9.
 */

public abstract class AbstractChargeStrategy implements ChargeStrategy {

    protected String entryTime;
    protected Date entryDate;

    /**
     * 构造函数
     *
     * @param entryTime 进场时间【yyyy.MM.dd HH:mm:ss】
     */
    public AbstractChargeStrategy(String entryTime) {
        this.entryTime = entryTime;
        DateFormat df = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
        try {
            this.entryDate = df.parse(entryTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取停车单价（n元/小时）
     *
     * @return
     */
    protected abstract double getSinglePrice();

    /**
     * 获取半天/12小时内最高价格
     *
     * @return
     */
    protected abstract double getHalfDayPeakPrice();

    /**
     * 获取全天/24小时内最高价格
     *
     * @return
     */
    protected abstract double getAllDayPeakPrice();

    /**
     * 获取当前费用
     *
     * @param consumed 进场当日已消费的费用(不包含此次停车费用)
     * @return
     */
    @Override
    public double getCurrentFees(double consumed) throws ParseException {
        //获取已停总小时数(向上取整)
        int totalHours = DateUtils.getHoursOfDifference(entryTime);
        int days = totalHours / 24;
        int hours = totalHours % 24;

        double fees = getSinglePrice();
        if (hours <= 12) {
            fees *= hours;
            if (fees > getHalfDayPeakPrice()) {
                fees = getHalfDayPeakPrice();
            }
        } else if (hours <= 24) {
            fees *= (hours - 12);
            fees += getHalfDayPeakPrice();
            if (fees > getAllDayPeakPrice()) {
                fees = getAllDayPeakPrice();
            }
        }

        if (fees < getSinglePrice()) {
            fees = getSinglePrice();
        }

        return fees + days * getAllDayPeakPrice();
    }

    /**
     * 获取下个计费点和下一个计费的费用(将新增价格)
     *
     * @return
     * @throws ParseException
     */
    @Override
    public NextBillingInfo getNextBillingTimeAndFees() throws ParseException {
        //获取已停总小时数(向上取整)
        int totalHours = DateUtils.getHoursOfDifference(entryTime);

        NextBillingInfo nextBillingInfo = doGetNextBillingTimeAndFees(totalHours);
        while (nextBillingInfo.fees <= 0) {
            ++totalHours;
            nextBillingInfo = doGetNextBillingTimeAndFees(totalHours);
        }

        return doGetNextBillingTimeAndFees(totalHours);
    }

    /**
     * 获取下个计费点和下一个计费的费用【内部执行函数】
     *
     * @return
     * @throws ParseException
     */
    private NextBillingInfo doGetNextBillingTimeAndFees(int totalHours) {
        //新增价格
        double feesOfNextBilling = doGetFeesOfNextBilling(totalHours);
        //下个计费点
        String nextBillingTime = doGetNextBillingTime(totalHours);

        return new NextBillingInfo(nextBillingTime, feesOfNextBilling);
    }

    /**
     * 获取下个计费点【内部函数】
     *
     * @param totalHours
     * @return
     */
    protected String doGetNextBillingTime(int totalHours) {
        Calendar entryCalendar = Calendar.getInstance();
        entryCalendar.setTime(entryDate);
        entryCalendar.add(Calendar.HOUR_OF_DAY, totalHours);
        entryCalendar.add(Calendar.MINUTE, 1);

        String timePoint = null;
        int days = totalHours / 24;
        int nextHours = totalHours % 24;

        if ((nextHours >= 0) && (nextHours <= 4)) {
            timePoint = getNextTimPoint(nextHours + 24 * days,
                    entryTime);
        }
        if ((nextHours > 4) && (nextHours <= 12)) {
            timePoint = getNextTimPoint(12 + 24 * days,
                    entryTime);
        }
        if ((nextHours > 12) && (nextHours <= 14)) {
            timePoint = getNextTimPoint(nextHours + 24 * days,
                    entryTime);
        }
        if ((nextHours > 14) && (nextHours <= 24)) {
            timePoint = getNextTimPoint(24 + 24 * days,
                    entryTime);
        }

        return timePoint;
    }

    /**
     * @param times     当前已停小时数
     * @param startTime 进场时间
     * @returnøØ
     */
    private String getNextTimPoint(int times, String startTime) {
        DateFormat df = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
        try {
            Date entryDate = df.parse(startTime);
            Calendar entryCalendar = Calendar.getInstance();
            entryCalendar.setTime(entryDate);
            entryCalendar.add(Calendar.HOUR_OF_DAY, times);
            entryCalendar.add(Calendar.MINUTE, 1);
            Date myDate = df.parse(df.format(entryCalendar.getTime()));

            return new SimpleDateFormat("MM.dd HH:mm").format(myDate);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }

    /**
     * 获取下一个计费的费用(将新增价格)【内部函数】
     *
     * @param totalHours
     * @return
     */
    protected double doGetFeesOfNextBilling(int totalHours) {
        int hours = (totalHours + 1) % 24;

        double newFees = 0;
        if (hours <= 12) {
            if (getSinglePrice() * hours <= getHalfDayPeakPrice()) {
                newFees = getSinglePrice();
            } else {
                newFees = getHalfDayPeakPrice() - getSinglePrice() * (hours - 1);
            }
        } else if (hours <= 24) {
            if (getSinglePrice() * (hours - 12) <= getAllDayPeakPrice()) {
                newFees = getSinglePrice();
            } else {
                newFees = getAllDayPeakPrice() - getSinglePrice() * (hours - 12 - 1);
            }
        }

        return newFees;
    }
}
