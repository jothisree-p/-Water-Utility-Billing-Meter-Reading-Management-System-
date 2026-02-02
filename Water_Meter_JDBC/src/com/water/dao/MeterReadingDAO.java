package com.water.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.water.bean.MeterReading;
import com.water.util.DBUtil;

public class MeterReadingDAO {

    public int generateReadingID() {
        Connection con = DBUtil.getDBConnection();
        try {
            PreparedStatement ps =
                con.prepareStatement("SELECT READING_SEQ.NEXTVAL FROM DUAL");
            ResultSet rs = ps.executeQuery();
            rs.next();
            return rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public boolean recordReading(MeterReading r) {
        Connection con = DBUtil.getDBConnection();
        String sql = "INSERT INTO METER_READING_TBL VALUES (?,?,?,?,?,?)";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, r.getReadingID());
            ps.setString(2, r.getConsumerID());
            ps.setString(3, r.getMeterNumber());
            ps.setDate(4, new java.sql.Date(r.getReadingDate().getTime()));
            ps.setDouble(5, r.getReadingValue());
            ps.setString(6, r.getRecordedBy());

            int rows = ps.executeUpdate();
            con.commit();
            return rows > 0;

        } catch (SQLException e) {
            try { con.rollback(); } catch (Exception ex) {}
            e.printStackTrace();
        }
        return false;
    }

    public MeterReading findLatestReading(String consumerID) {
        Connection con = DBUtil.getDBConnection();
        String sql =
            "SELECT * FROM METER_READING_TBL WHERE CONSUMER_ID=? " +
            "ORDER BY READING_DATE DESC";

        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, consumerID);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                MeterReading r = new MeterReading();
                r.setReadingID(rs.getInt(1));
                r.setConsumerID(rs.getString(2));
                r.setMeterNumber(rs.getString(3));
                r.setReadingDate(rs.getDate(4));
                r.setReadingValue(rs.getDouble(5));
                r.setRecordedBy(rs.getString(6));
                return r;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<MeterReading> viewReadingHistory(String consumerID) {
        List<MeterReading> list = new ArrayList<>();
        Connection con = DBUtil.getDBConnection();
        try {
            PreparedStatement ps =
                con.prepareStatement(
                    "SELECT * FROM METER_READING_TBL WHERE CONSUMER_ID=? ORDER BY READING_DATE");
            ps.setString(1, consumerID);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                MeterReading r = new MeterReading();
                r.setReadingID(rs.getInt(1));
                r.setConsumerID(rs.getString(2));
                r.setMeterNumber(rs.getString(3));
                r.setReadingDate(rs.getDate(4));
                r.setReadingValue(rs.getDouble(5));
                r.setRecordedBy(rs.getString(6));
                list.add(r);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
