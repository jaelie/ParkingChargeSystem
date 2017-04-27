package com.jye.parkingchargesystem;

import com.jye.parkingchargesystem.charge.AbstractChargeStrategy;
import com.jye.parkingchargesystem.charge.SmallVehicleChargeStrategy;
import com.jye.parkingchargesystem.charge.SpecialVehicleChargeStrategy;
import com.jye.parkingchargesystem.charge.vo.NextBillingInfo;

import java.text.ParseException;

/**
 * Created by jye on 2017/2/9.
 */

public class ChargeTest {

    public static void main(String[] args) {
        AbstractChargeStrategy strategy = new SpecialVehicleChargeStrategy("2017.02.07 19:23:00");
        try {
            double currentFees = strategy.getCurrentFees(0);
            System.out.println("当前费用: " + currentFees);
            System.out.println("------------------------华丽的分割线------------------------");
            NextBillingInfo nextBillingInfo = strategy.getNextBillingTimeAndFees();
            String nextBillingTime = nextBillingInfo.time;
            double feesOfNextBilling = nextBillingInfo.fees;
            System.out.println("下个计费点: " + nextBillingTime);
            System.out.println("将新增价格: " + feesOfNextBilling);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
