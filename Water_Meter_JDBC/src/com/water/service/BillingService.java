package com.water.service;

import java.util.Date;
import java.util.List;

import com.water.bean.*;
import com.water.dao.*;
import com.water.util.*;

public class BillingService {

    private ConsumerDAO consumerDAO = new ConsumerDAO();
    private MeterReadingDAO meterDAO = new MeterReadingDAO();
    private BillDAO billDAO = new BillDAO();

    public MeterReadingDAO getMeterReadingService() {
        return meterDAO;
    }

    public Consumer viewConsumerDetails(String consumerID) {
        return consumerDAO.findConsumer(consumerID);
    }

    public List<Consumer> viewAllConsumers() {
        return consumerDAO.viewAllConsumers();
    }

    public boolean addNewConsumer(Consumer c) throws ValidationException {
        if (c == null || c.getConsumerID() == null || c.getConsumerID().trim().isEmpty()) {
            throw new ValidationException();
        }
        return consumerDAO.insertConsumer(c);
    }

    @SuppressWarnings("deprecation")
    public boolean recordMeterReading(String consumerID, String meterNumber,
            Date readingDate, double readingValue, String recordedBy)
            throws ValidationException, ReadingAlreadyExistsException {

        if (consumerID == null || meterNumber == null || readingDate == null
                || readingValue < 0) {
            throw new ValidationException();
        }
        MeterReading last = meterDAO.findLatestReading(consumerID);
        if (last != null &&
            last.getReadingDate().getMonth() == readingDate.getMonth() &&
            last.getReadingDate().getYear() == readingDate.getYear()) {

            throw new ReadingAlreadyExistsException();
        }

        MeterReading mr = new MeterReading();
        mr.setReadingID(meterDAO.generateReadingID()); 
        mr.setConsumerID(consumerID);
        mr.setMeterNumber(meterNumber);
        mr.setReadingDate(readingDate);
        mr.setReadingValue(readingValue);
        mr.setRecordedBy(recordedBy);

        return meterDAO.recordReading(mr);
    }

    public boolean generateMonthlyBill(String consumerID,
            MeterReading prev, MeterReading curr) throws ValidationException {

        if (consumerID == null || prev == null || curr == null) {
            throw new ValidationException();
        }

        if (curr.getReadingValue() < prev.getReadingValue()) {
            throw new ValidationException();
        }

        double units = curr.getReadingValue() - prev.getReadingValue();
        double amount = units * 5;
        Bill b = new Bill();
        b.setBillID(billDAO.generateBillID());
        b.setConsumerID(consumerID);
        b.setBillingPeriodFrom(prev.getReadingDate());
        b.setBillingPeriodTo(curr.getReadingDate());
        b.setUnitsConsumed(units);
        b.setAmount(amount);
        b.setBillDate(new Date());
        b.setStatus("PENDING");

        return billDAO.recordBill(b);
    }

    public boolean cancelBill(int billID) throws ActiveBillException {

        if (billID <= 0) {
            return false;
        }

        Bill b = billDAO.findBill(billID);
        if (b == null) {
            return false;
        }

        if ("PAID".equals(b.getStatus())) {
            throw new ActiveBillException();
        }

        if ("CANCELLED".equals(b.getStatus())) {
            return false;
        }

        return billDAO.updateBillStatus(billID, "CANCELLED");
    }
}
