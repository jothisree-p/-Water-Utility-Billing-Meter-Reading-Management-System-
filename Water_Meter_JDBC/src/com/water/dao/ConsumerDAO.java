package com.water.dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.water.bean.Consumer;
import com.water.util.DBUtil;

public class ConsumerDAO {

    public Consumer findConsumer(String consumerID) {
        Connection con = DBUtil.getDBConnection();
        String sql = "SELECT * FROM CONSUMER_TBL WHERE CONSUMER_ID=?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, consumerID);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Consumer c = new Consumer();
                c.setConsumerID(rs.getString(1));
                c.setFullName(rs.getString(2));
                c.setAddress(rs.getString(3));
                c.setMeterNumber(rs.getString(4));
                c.setConnectionType(rs.getString(5));
                c.setOutstandingBalance(rs.getDouble(6));
                return c;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Consumer> viewAllConsumers() {
        List<Consumer> list = new ArrayList<>();
        Connection con = DBUtil.getDBConnection();
        try {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM CONSUMER_TBL");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Consumer c = new Consumer();
                c.setConsumerID(rs.getString(1));
                c.setFullName(rs.getString(2));
                c.setAddress(rs.getString(3));
                c.setMeterNumber(rs.getString(4));
                c.setConnectionType(rs.getString(5));
                c.setOutstandingBalance(rs.getDouble(6));
                list.add(c);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean insertConsumer(Consumer consumer) {
        Connection con = DBUtil.getDBConnection();
        String sql = "INSERT INTO CONSUMER_TBL VALUES (?,?,?,?,?,?)";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, consumer.getConsumerID());
            ps.setString(2, consumer.getFullName());
            ps.setString(3, consumer.getAddress());
            ps.setString(4, consumer.getMeterNumber());
            ps.setString(5, consumer.getConnectionType());
            ps.setDouble(6, consumer.getOutstandingBalance());
            int r = ps.executeUpdate();
            con.commit();
            return r > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateOutstandingBalance(String consumerID, double newBalance) {
        Connection con = DBUtil.getDBConnection();
        try {
            PreparedStatement ps = con.prepareStatement(
                    "UPDATE CONSUMER_TBL SET OUTSTANDING_BALANCE=? WHERE CONSUMER_ID=?");
            ps.setDouble(1, newBalance);
            ps.setString(2, consumerID);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
