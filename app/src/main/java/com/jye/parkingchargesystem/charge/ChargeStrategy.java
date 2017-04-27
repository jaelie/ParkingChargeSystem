package com.jye.parkingchargesystem.charge;

import com.jye.parkingchargesystem.charge.vo.NextBillingInfo;

import java.text.ParseException;

/**
 * 收费策略接口
 * Created by jye on 2017/2/9.
 */

public interface ChargeStrategy {

    /**
     * 获取当前费用
     *
     * @param consumed 进场当日已消费的费用(不包含此次停车费用)
     * @return
     */
    double getCurrentFees(double consumed) throws ParseException;

    /**
     * 获取下个计费点和下一个计费的费用(将新增价格)
     *
     * @return
     * @throws ParseException
     */
    NextBillingInfo getNextBillingTimeAndFees() throws ParseException;
}
