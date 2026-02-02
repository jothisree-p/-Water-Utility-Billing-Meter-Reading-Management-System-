package com.water.bean;

import java.util.Date;

public class Bill {

    private int billID;
    private String consumerID;
    private Date billingPeriodFrom;
    private Date billingPeriodTo;
    private double unitsConsumed;
    private double amount;
    private Date billDate;
    private String status;

    public int getBillID() {
        return billID;
    }
    public void setBillID(int billID) {
        this.billID = billID;
    }

    public String getConsumerID() {
        return consumerID;
    }
    public void setConsumerID(String consumerID) {
        this.consumerID = consumerID;
    }

    public Date getBillingPeriodFrom() {
        return billingPeriodFrom;
    }
    public void setBillingPeriodFrom(Date billingPeriodFrom) {
        this.billingPeriodFrom = billingPeriodFrom;
    }

    public Date getBillingPeriodTo() {
        return billingPeriodTo;
    }
    public void setBillingPeriodTo(Date billingPeriodTo) {
        this.billingPeriodTo = billingPeriodTo;
    }

    public double getUnitsConsumed() {
        return unitsConsumed;
    }
    public void setUnitsConsumed(double unitsConsumed) {
        this.unitsConsumed = unitsConsumed;
    }

    public double getAmount() {
        return amount;
    }
    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Date getBillDate() {
        return billDate;
    }
    public void setBillDate(Date billDate) {
        this.billDate = billDate;
    }

    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
}
