package com.jye.parkingchargesystem.charge.vo;

/**
 * 下个计费信息
 * Created by jye on 2017/2/9.
 */

public class NextBillingInfo {
    /**
     * 下个计费点
     */
    public String time;
    /**
     * 新增价格
     */
    public double fees;

    public NextBillingInfo(String time, double fees) {
        this.time = time;
        this.fees = fees;
    }
}
