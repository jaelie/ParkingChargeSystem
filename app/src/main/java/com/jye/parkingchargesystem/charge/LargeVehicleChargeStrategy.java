package com.jye.parkingchargesystem.charge;

/**
 * 大型车收费策略
 * Created by jye on 2017/2/9.
 */

public class LargeVehicleChargeStrategy extends AbstractChargeStrategy {
    //停车费单价：5.0/小时
    private static final double SINGLE_PRICE = 5.0;
    //半天/12小时内最高价格
    private static final double HALF_DAY_PEAK_PRICE = 20.0;
    //全天/24小时内最高价格
    private static final double ALL_DAY_PEAK_PRICE = 30.0;

    /**
     * 构造函数
     *
     * @param entryTime 进场时间【yyyy.MM.dd HH:mm:ss】
     */
    public LargeVehicleChargeStrategy(String entryTime) {
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

}
