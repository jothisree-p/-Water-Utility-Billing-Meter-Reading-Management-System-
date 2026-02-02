package com.water.app;

import java.util.Date;
import java.util.Scanner;

import com.water.bean.MeterReading;
import com.water.service.BillingService;

public class WaterMain {

    private static BillingService billingService;
    public static void main(String[] args) {

        billingService = new BillingService();
        Scanner sc = new Scanner(System.in);

        System.out.println("--- Water Utility Billing Console ---");

        // TEST 1: Duplicate reading (should fail)
        try {
            boolean r =
                billingService.recordMeterReading(
                    "CUS1001","MTR-5001",
                    new Date(2025-1900,2,28),
                    1300,"MeterReader02");
            System.out.println(r ? "RECORDED" : "FAILED");
        } catch(Exception e) {
            System.out.println(e);
        }

        // TEST 2: Generate bill
        try {
            MeterReading prev =
                billingService.getMeterReadingService()
                              .findLatestReading("CUS1001");

            MeterReading curr = new MeterReading();
            curr.setReadingValue(1350);
            curr.setReadingDate(new Date(2025-1900,3,31));

            boolean r =
                billingService.generateMonthlyBill("CUS1001", prev, curr);
            System.out.println(r ? "BILL GENERATED" : "FAILED");
        } catch(Exception e) {
            System.out.println(e);
        }

        // TEST 3: Cancel bill
        try {
            boolean r = billingService.cancelBill(610002);
            System.out.println(r ? "CANCELLED" : "FAILED");
        } catch(Exception e) {
            System.out.println(e);
        }

        sc.close();
    }
}
