package com.jye.parkingchargesystem.charge;

import com.jye.parkingchargesystem.charge.vo.NextBillingInfo;

import java.text.ParseException;

/**
 * 特殊车辆收费策略
 * Created by jye on 2017/2/9.
 */

public class SpecialVehicleChargeStrategy extends AbstractChargeStrategy {
    //停车费单价：4.0/小时
    private static final double SINGLE_PRICE = 10.0;
    //半天/12小时内最高价格
    private static final double HALF_DAY_PEAK_PRICE = 10.0;
    //全天/24小时内最高价格
    private static final double ALL_DAY_PEAK_PRICE = 10.0;

    /**
     * 构造函数
     *
     * @param entryTime 进场时间【yyyy.MM.dd HH:mm:ss】
     */
    public SpecialVehicleChargeStrategy(String entryTime) {
        super(entryTime);
    }

    @Override
    protected double getSinglePrice() {
        return SINGLE_PRICE;
    }

    @Override
    protected double getHalfDayPeakPrice() {
        return HALF_DAY_PEAK_PRICE;
    }

    @Override
    protected double getAllDayPeakPrice() {
        return ALL_DAY_PEAK_PRICE;
    }

    /**
     * 获取当前费用
     *
     * @param consumed 进场当日已消费的费用(不包含此次停车费用)
     * @return
     */
    @Override
    public double getCurrentFees(double consumed) throws ParseException {
        return 0;
    }

    /**
     * 获取下个计费点和下一个计费的费用(将新增价格)
     *
     * @return
     * @throws ParseException
     */
    @Override
    public NextBillingInfo getNextBillingTimeAndFees() throws ParseException {
        return new NextBillingInfo(DateUtils.getCurrentTime(), 0);
    }
}
