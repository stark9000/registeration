/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 *
 */
public class SqlLite {

    private Connection conn3ction = null;
    private static SqlLite _INSTANCE = null;
    private Statement stmt = null;
    private ResultSet res = null;

    private SqlLite() {
    }

    public static synchronized SqlLite getInstence() {
        if (_INSTANCE == null) {
            _INSTANCE = new SqlLite();
        }
        return _INSTANCE;
    }

    public Connection Connect() {
        try {
            if (conn3ction == null || conn3ction.isClosed()) {
                Class.forName("org.sqlite.JDBC");
                conn3ction = DriverManager.getConnection("jdbc:sqlite:registration.db");
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                if (conn3ction == null) {
                    conn3ction.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return conn3ction;
    }

    public boolean Write_setCommand(String sql) {
        try {
            stmt = conn3ction.createStatement();
            stmt.setQueryTimeout(30);
            stmt.executeUpdate(sql);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public ResultSet Read_setCommand(String sql) {
        try {
            stmt = conn3ction.createStatement();
            stmt.executeUpdate(sql);
            res = stmt.executeQuery(sql);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return res;
    }

    public boolean Ping() {
        try {
            Read_setCommand("/* ping */ SELECT 1").close();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

}
