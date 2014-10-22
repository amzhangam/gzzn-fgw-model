package com.gzzn.fgw.service.project;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class ConnectionUtils {
    private static String driver = null;
    private static String url = null;
    private static String dbUser = null;
    private static String dbPassword = null;
    static {
        Properties prop = new Properties();
        try {
            prop.load(ConnectionUtils.class.getClassLoader().getResourceAsStream("jdbc.properties"));
            driver = prop.getProperty("jdbc.driver");
            url = prop.getProperty("jdbc.url");
            dbUser = prop.getProperty("jdbc.username");
            dbPassword = prop.getProperty("jdbc.password");
            Class.forName(driver);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, dbUser, dbPassword);
    }
    
    public static void main(String[] args) {
        Connection conn = null;
        try {
            conn = ConnectionUtils.getConnection();
            conn.setAutoCommit(false);
            Statement stat = conn.createStatement();
            System.out.println(stat.executeUpdate("update SYS_DX set SFBJ=2 where DX_ID=50061750"));
            
            conn.commit();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
