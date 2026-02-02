package com.water.bean;

import java.util.Date;

public class MeterReading {

    private int readingID;
    private String consumerID;
    private String meterNumber;
    private Date readingDate;
    private double readingValue;
    private String recordedBy;

    public int getReadingID() {
        return readingID;
    }
    public void setReadingID(int readingID) {
        this.readingID = readingID;
    }
    public String getConsumerID() {
        return consumerID;
    }
    public void setConsumerID(String consumerID) {
        this.consumerID = consumerID;
    }
    public String getMeterNumber() {
        return meterNumber;
    }
    public void setMeterNumber(String meterNumber) {
        this.meterNumber = meterNumber;
    }
    public Date getReadingDate() {
        return readingDate;
    }
    public void setReadingDate(Date readingDate) {
        this.readingDate = readingDate;
    }
    public double getReadingValue() {
        return readingValue;
    }
    public void setReadingValue(double readingValue) {
        this.readingValue = readingValue;
    }
    public String getRecordedBy() {
        return recordedBy;
    }
    public void setRecordedBy(String recordedBy) {
        this.recordedBy = recordedBy;
    }
}

