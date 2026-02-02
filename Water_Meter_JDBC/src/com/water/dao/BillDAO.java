package com.water.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.water.bean.Bill;
import com.water.util.DBUtil;

public class BillDAO {

    public int generateBillID() {
        Connection con = DBUtil.getDBConnection();
        String query = "SELECT BILL_SEQ.NEXTVAL FROM DUAL";

        try {
            PreparedStatement ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public boolean recordBill(Bill bill) {
        Connection con = DBUtil.getDBConnection();
        String query = "INSERT INTO BILL_TBL VALUES (?,?,?,?,?,?,?,?)";

        try {
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, bill.getBillID());
            ps.setString(2, bill.getConsumerID());
            ps.setDate(3, new java.sql.Date(bill.getBillingPeriodFrom().getTime()));
            ps.setDate(4, new java.sql.Date(bill.getBillingPeriodTo().getTime()));
            ps.setShort(5, (short) bill.getUnitsConsumed());    
            ps.setDouble(6, bill.getAmount());
            ps.setDate(7, new java.sql.Date(bill.getBillDate().getTime()));
            ps.setString(8, bill.getStatus());

            return ps.executeUpdate() > 0;


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateBillStatus(int billID, String status) {
        Connection con = DBUtil.getDBConnection();
        String query = "UPDATE BILL_TBL SET STATUS=? WHERE BILL_ID=?";

        try {
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, status);
            ps.setInt(2, billID);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Bill findBill(int billID) {
        Bill b = null;

        try (Connection con = DBUtil.getDBConnection();
             PreparedStatement ps =
                 con.prepareStatement(
                     "SELECT * FROM BILL_TBL WHERE BILL_ID = ?")) {

            ps.setInt(1, billID);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                b = new Bill();
                b.setBillID(rs.getInt("BILL_ID"));
                b.setConsumerID(rs.getString("CONSUMER_ID"));
                b.setBillingPeriodFrom(rs.getDate("BILLING_PERIOD_FROM"));
                b.setBillingPeriodTo(rs.getDate("BILLING_PERIOD_TO"));
                b.setUnitsConsumed(rs.getDouble("UNITS_CONSUMED"));
                b.setAmount(rs.getDouble("AMOUNT"));
                b.setBillDate(rs.getDate("BILL_DATE")); 
                b.setStatus(rs.getString("STATUS"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return b;
    }

}
